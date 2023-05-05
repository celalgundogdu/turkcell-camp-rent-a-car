package com.turkcellcamp.rentacar.business.concretes;

import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreateCarRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdateCarRequest;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreateCarResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllCarsResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetCarResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdateCarResponse;
import com.turkcellcamp.rentacar.business.rules.CarBusinessRules;
import com.turkcellcamp.rentacar.entities.Car;
import com.turkcellcamp.rentacar.entities.Model;
import com.turkcellcamp.rentacar.entities.enums.State;
import com.turkcellcamp.rentacar.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CarServiceImplTest {

    private CarRepository carRepository;
    private CarBusinessRules carBusinessRules;
    private ModelMapper mapper;
    private CarService carService;
    private List<Car> carList;

    @BeforeEach
    void setUp() {
        carRepository = mock(CarRepository.class);
        carBusinessRules = mock(CarBusinessRules.class);
        mapper = mock(ModelMapper.class);
        carService =  new CarServiceImpl(carRepository, carBusinessRules, mapper);

        Car car1 = new Car(1, 2020, "35 ABC 35", State.AVAILABLE, 1000, new Model(), null, null);
        Car car2 = new Car(2, 2020, "35 AB 35", State.AVAILABLE, 2000, new Model(), null, null);
        Car car3 = new Car(3, 2020, "35 A 35", State.RENTED, 1000, new Model(), null, null);
        carList = new ArrayList<>(List.of(car1, car2, car3));
    }

    @Test
    void shouldGetAllCars_whenStateIsNull() {
        State state = null;

        GetAllCarsResponse response1 = new GetAllCarsResponse(carList.get(0).getId(), carList.get(0).getModelYear(),
                carList.get(0).getPlate(), carList.get(0).getState(), carList.get(0).getDailyPrice(), carList.get(0).getModel().getName());
        GetAllCarsResponse response2 = new GetAllCarsResponse(carList.get(1).getId(), carList.get(1).getModelYear(),
                carList.get(1).getPlate(), carList.get(1).getState(), carList.get(1).getDailyPrice(), carList.get(1).getModel().getName());
        GetAllCarsResponse response3 = new GetAllCarsResponse(carList.get(2).getId(), carList.get(2).getModelYear(),
                carList.get(2).getPlate(), carList.get(2).getState(), carList.get(2).getDailyPrice(), carList.get(2).getModel().getName());
        List<GetAllCarsResponse> response = new ArrayList<>(List.of(response1, response2, response3));

        when(carRepository.findAll()).thenReturn(carList);
        when(mapper.map(carList.get(0), GetAllCarsResponse.class)).thenReturn(response1);
        when(mapper.map(carList.get(1), GetAllCarsResponse.class)).thenReturn(response2);
        when(mapper.map(carList.get(2), GetAllCarsResponse.class)).thenReturn(response3);

        List<GetAllCarsResponse> actual = carService.getAll(null);

        assertEquals(response, actual);

        verify(carRepository, times(1)).findAll();
        verify(carRepository, times(0)).findAllByState(any(State.class));
        verify(mapper, times(3)).map(any(Car.class), eq(GetAllCarsResponse.class));
    }

    @Test
    void shouldGetAllCarsByState_whenStateIsNotNull() {
        State state = State.AVAILABLE;

        GetAllCarsResponse response1 = new GetAllCarsResponse(carList.get(0).getId(), carList.get(0).getModelYear(),
                carList.get(0).getPlate(), carList.get(0).getState(), carList.get(0).getDailyPrice(), carList.get(0).getModel().getName());
        GetAllCarsResponse response2 = new GetAllCarsResponse(carList.get(1).getId(), carList.get(1).getModelYear(),
                carList.get(1).getPlate(), carList.get(1).getState(), carList.get(1).getDailyPrice(), carList.get(1).getModel().getName());
        List<GetAllCarsResponse> response = new ArrayList<>(List.of(response1, response2));

        when(carRepository.findAllByState(state)).thenReturn(List.of(carList.get(0), carList.get(1)));
        when(mapper.map(carList.get(0), GetAllCarsResponse.class)).thenReturn(response1);
        when(mapper.map(carList.get(1), GetAllCarsResponse.class)).thenReturn(response2);

        List<GetAllCarsResponse> actual = carService.getAll(state);

        assertEquals(response, actual);

        verify(carRepository, times(0)).findAll();
        verify(carRepository, times(1)).findAllByState(any(State.class));
        verify(mapper, times(2)).map(any(Car.class), eq(GetAllCarsResponse.class));
    }

    @Test
    void shouldAddCar_whenRequestIsValid() {
        CreateCarRequest request = new CreateCarRequest(2020, "35 A 35", 1000, 1);

        Car car = new Car();
        car.setModelYear(request.getModelYear());
        car.setPlate(request.getPlate());
        car.setDailyPrice(request.getDailyPrice());

        Car createdCar = new Car(123, car.getModelYear(), car.getPlate(), car.getState(), car.getDailyPrice(), null, null, null);

        CreateCarResponse response = new CreateCarResponse(createdCar.getId(), createdCar.getModelYear(), createdCar.getPlate(), createdCar.getState(), createdCar.getDailyPrice());

        when(mapper.map(request, Car.class)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(createdCar);
        when(mapper.map(createdCar, CreateCarResponse.class)).thenReturn(response);

        CreateCarResponse actualResponse = carService.add(request);

        assertEquals(response, actualResponse);

        verify(carRepository, times(1)).save(car);
    }

    @Test
    void shouldUpdateCar_whenCarExistsById() {
        int id = 1;

        UpdateCarRequest request = new UpdateCarRequest(2020, "35 A 35", State.RENTED,1000, 1);

        Car car = new Car();
        car.setModelYear(request.getModelYear());
        car.setPlate(request.getPlate());
        car.setState(request.getState());
        car.setDailyPrice(request.getDailyPrice());

        Car updatedCar = new Car(id, car.getModelYear(), car.getPlate(), car.getState(), car.getDailyPrice(), null, null, null);

        UpdateCarResponse response = new UpdateCarResponse(updatedCar.getId(), updatedCar.getModelYear(), updatedCar.getPlate(), updatedCar.getState(), updatedCar.getDailyPrice(), null);

        doNothing().when(carBusinessRules).checkIfCarExistsById(id);
        when(mapper.map(request, Car.class)).thenReturn(car);
        when(carRepository.save(car)).thenReturn(updatedCar);
        when(mapper.map(updatedCar, UpdateCarResponse.class)).thenReturn(response);

        UpdateCarResponse actual = carService.update(id, request);

        assertEquals(response, actual);
    }

    @Test
    void shouldReturnCar_whenCarExistsById() {
        // arrange
        int id = 1;

        Car car = new Car(1, 2020, "35 ABC 35", State.AVAILABLE, 1000, new Model(), null, null);

        GetCarResponse response = new GetCarResponse(car.getId(), car.getModelYear(), car.getPlate(),car.getState(),
                car.getDailyPrice(), null, null);

        doNothing().when(carBusinessRules).checkIfCarExistsById(id);
        when(carRepository.findById(id)).thenReturn(Optional.of(car));
        when(mapper.map(car, GetCarResponse.class)).thenReturn(response);

        // act
        GetCarResponse actual = carService.getById(id);

        // assert
        assertEquals(response, actual);

        verify(carRepository, times(1)).findById(id);
    }

    @Test
    void shouldDeleteCar_whenCarExistsById() {
        int id = 1;

        doNothing().when(carBusinessRules).checkIfCarExistsById(id);
        doNothing().when(carRepository).deleteById(id);

        carService.delete(id);

        verify(carRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldChangeStateOfCar_whenCarExistsById() {
        int id = 1;
        State newState = State.MAINTENANCE;
        Car car = new Car(1, 2020, "35 ABC 35", State.AVAILABLE, 1000, new Model(), null, null);

        doNothing().when(carBusinessRules).checkIfCarExistsById(id);
        when(carRepository.findById(id)).thenReturn(Optional.of(car));
        when(carRepository.save(car)).thenReturn(car);

        carService.changeState(id, newState);

        assertEquals(newState, car.getState());
        verify(carRepository, times(1)).save(car);
    }
}
