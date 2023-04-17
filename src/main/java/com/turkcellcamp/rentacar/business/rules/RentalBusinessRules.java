package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.entities.enums.State;
import com.turkcellcamp.rentacar.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalBusinessRules {

    private final RentalRepository rentalRepository;

    public void checkIfRentalExistsById(int id) {
        if (!rentalRepository.existsById(id)) {
            throw new RuntimeException(Messages.Rental.NOT_EXISTS);
        }
    }

    public void checkIfCarIsAvailable(State state) {
        if (!state.equals(State.AVAILABLE)) {
            throw new RuntimeException(Messages.Car.NOT_AVAILABLE);
        }
    }
}
