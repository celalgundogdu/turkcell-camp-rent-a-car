package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.core.exceptions.EntityAlreadyExistsException;
import com.turkcellcamp.rentacar.core.exceptions.EntityNotFoundException;
import com.turkcellcamp.rentacar.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarBusinessRulesTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarBusinessRules carBusinessRules;

    @Test
    void shouldDoNothing_whenCarExistsById() {
        int id = 1;

        when(carRepository.existsById(id)).thenReturn(true);

        carBusinessRules.checkIfCarExistsById(id);

        verify(carRepository, times(1)).existsById(id);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenCarNotExistById() {
        int id = 1;

        when(carRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> carBusinessRules.checkIfCarExistsById(id));

        verify(carRepository, times(1)).existsById(id);
    }

    @Test
    void shouldDoNothing_whenCarExistsByPlate() {
        String plate = "35 ABC 35";

        when(carRepository.existsByPlateIgnoreCase(plate)).thenReturn(false);

        carBusinessRules.checkIfCarExistsByPlate(plate);

        verify(carRepository, times(1)).existsByPlateIgnoreCase(plate);
    }

    @Test
    void shouldThrowEntityAlreadyExistsException_whenCarNotExistsByPlate() {
        String plate = "35 ABC 35";

        when(carRepository.existsByPlateIgnoreCase(plate)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> carBusinessRules.checkIfCarExistsByPlate(plate));

        verify(carRepository, times(1)).existsByPlateIgnoreCase(plate);
    }
}
