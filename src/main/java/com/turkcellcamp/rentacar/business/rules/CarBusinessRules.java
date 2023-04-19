package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.core.exceptions.EntityAlreadyExistsException;
import com.turkcellcamp.rentacar.core.exceptions.EntityNotFoundException;
import com.turkcellcamp.rentacar.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarBusinessRules {

    private final CarRepository carRepository;

    public void checkIfCarExistsById(int id) {
        if (!carRepository.existsById(id)) {
            throw new EntityNotFoundException(Messages.Car.NOT_EXISTS);
        }
    }

    public void checkIfCarExistsByPlate(String plate) {
        if (carRepository.existsByPlateIgnoreCase(plate)) {
            throw new EntityAlreadyExistsException(Messages.Car.ALREADY_EXISTS);
        }
    }
}
