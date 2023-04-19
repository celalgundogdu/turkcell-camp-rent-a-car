package com.turkcellcamp.rentacar.validation;

import com.turkcellcamp.rentacar.repository.CarRepository;
import com.turkcellcamp.rentacar.validation.UniquePlate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniquePlateValidator implements ConstraintValidator<UniquePlate, String> {

    private final CarRepository carRepository;

    public UniquePlateValidator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean isExists = carRepository.existsByPlateIgnoreCase(s);
        return !isExists;
    }
}
