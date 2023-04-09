package com.turkcellcamp.rentacar.business.concretes;

import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreateCarRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdateCarRequest;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreateCarResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllCarsResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetCarResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdateCarResponse;
import com.turkcellcamp.rentacar.entities.Car;
import com.turkcellcamp.rentacar.entities.enums.State;
import com.turkcellcamp.rentacar.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper mapper;

    @Override
    public List<GetAllCarsResponse> getAll(State state) {
        List<Car> carList;
        if (state == null) {
            carList = carRepository.findAll();
        } else {
            carList = carRepository.findAllByState(state);
        }

        List<GetAllCarsResponse> response = carList
                .stream()
                .map(car -> mapper.map(car, GetAllCarsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetCarResponse getById(int id) {
        checkIfCarExistsById(id);
        Car car = carRepository.findById(id).orElseThrow();

        GetCarResponse response = mapper.map(car, GetCarResponse.class);
        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        checkIfCarExistsByPlate(request.getPlate());

        Car car = mapper.map(request, Car.class);
        car.setId(0);
        car.setState(State.AVAILABLE);
        Car createdCar = carRepository.save(car);

        CreateCarResponse response = mapper.map(createdCar, CreateCarResponse.class);
        return response;
    }

    @Override
    public UpdateCarResponse update(int id, UpdateCarRequest request) {
        checkIfCarExistsById(id);

        Car car = mapper.map(request, Car.class);
        car.setId(id);
        Car updatedCar = carRepository.save(car);

        UpdateCarResponse response = mapper.map(updatedCar, UpdateCarResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        checkIfCarExistsById(id);
        carRepository.deleteById(id);
    }

    @Override
    public void changeState(int carId, State state) {
        checkIfCarExistsById(carId);
        Car car = carRepository.findById(carId).orElseThrow();
        car.setState(state);
        carRepository.save(car);
    }

    private void checkIfCarExistsById(int id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car does not exists with id: " + id);
        }
    }

    private void checkIfCarExistsByPlate(String plate) {
        if (carRepository.existsByPlateIgnoreCase(plate)) {
            throw new RuntimeException("Car already exists");
        }
    }
}
