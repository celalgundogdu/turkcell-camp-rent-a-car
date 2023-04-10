package com.turkcellcamp.rentacar.business.concretes;

import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.business.abstracts.RentalService;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreateRentalRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdateRentalRequest;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreateRentalResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllRentalsResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetRentalResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdateRentalResponse;
import com.turkcellcamp.rentacar.core.dto.CreateRentalPaymentRequest;
import com.turkcellcamp.rentacar.entities.Rental;
import com.turkcellcamp.rentacar.entities.enums.State;
import com.turkcellcamp.rentacar.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CarService carService;
    private final PaymentService paymentService;
    private final ModelMapper mapper;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        List<Rental> rentalList = rentalRepository.findAll();

        List<GetAllRentalsResponse> response = rentalList.stream()
                .map(rental -> mapper.map(rental, GetAllRentalsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetRentalResponse getById(int id) {
        checkIfRentalExistsById(id);
        Rental rental = rentalRepository.findById(id).orElseThrow();

        GetRentalResponse response = mapper.map(rental, GetRentalResponse.class);
        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        checkIfCarIsAvailable(request.getCarId());
        Rental rental = mapper.map(request, Rental.class);
        rental.setId(0);
        rental.setTotalPrice(calculateTotalPrice(rental));
        rental.setStartDate(LocalDateTime.now());

        CreateRentalPaymentRequest paymentRequest = new CreateRentalPaymentRequest();
        mapper.map(request.getPaymentRequest(), paymentRequest);
        paymentRequest.setPrice(calculateTotalPrice(rental));
        paymentService.processRentalPayment(paymentRequest);

        Rental createdRental = rentalRepository.save(rental);
        carService.changeState(request.getCarId(), State.RENTED);

        CreateRentalResponse response = mapper.map(createdRental, CreateRentalResponse.class);
        return response;
    }

    @Override
    public UpdateRentalResponse update(int id, UpdateRentalRequest request) {
        checkIfRentalExistsById(id);
        Rental rental = mapper.map(request, Rental.class);
        rental.setId(id);
        rental.setTotalPrice(calculateTotalPrice(rental));
        Rental updatedRental = rentalRepository.save(rental);

        UpdateRentalResponse response = mapper.map(updatedRental, UpdateRentalResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        checkIfRentalExistsById(id);
        int carId = rentalRepository.findById(id).get().getCar().getId();
        carService.changeState(carId, State.AVAILABLE);
        rentalRepository.deleteById(id);
    }

    private void checkIfRentalExistsById(int id) {
        if (!rentalRepository.existsById(id)) {
            throw new RuntimeException("Rental does not exists");
        }
    }

    private void checkIfCarIsAvailable(int carId) {
        if (!carService.getById(carId).getState().equals(State.AVAILABLE)) {
            throw new RuntimeException("Car is not available");
        }
    }

    private double calculateTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
    }
}
