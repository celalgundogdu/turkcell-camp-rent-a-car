package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarBusinessRules {

    private final CarRepository carRepository;

    public void checkIfCarExistsById(int id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException(Messages.Car.NOT_EXISTS);
        }
    }

    public void checkIfCarExistsByPlate(String plate) {
        if (carRepository.existsByPlateIgnoreCase(plate)) {
            throw new RuntimeException(Messages.Car.ALREADY_EXISTS);
        }
    }
}
