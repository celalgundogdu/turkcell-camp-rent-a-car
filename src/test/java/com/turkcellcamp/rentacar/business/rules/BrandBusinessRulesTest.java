package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.core.exceptions.EntityAlreadyExistsException;
import com.turkcellcamp.rentacar.core.exceptions.EntityNotFoundException;
import com.turkcellcamp.rentacar.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BrandBusinessRulesTest {

    @Mock
    private BrandRepository brandRepository;

    private BrandBusinessRules brandBusinessRules;

    @BeforeEach
    void setUp() {
        brandRepository = mock(BrandRepository.class);
        brandBusinessRules = new BrandBusinessRules(brandRepository);
    }

    @Test
    void shouldDoNothing_whenBrandExistsById() {
        int id = 1;

        when(brandRepository.existsById(id)).thenReturn(true);

        brandBusinessRules.checkIfBrandExistsById(id);

        verify(brandRepository, times(1)).existsById(id);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenBrandNotExistsById() {
        int id = 1;

        when(brandRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> brandBusinessRules.checkIfBrandExistsById(id));

        verify(brandRepository, times(1)).existsById(id);
    }

    @Test
    void shouldDoNothing_whenBrandExistsByName() {
        String name = "BMW";

        when(brandRepository.existsByNameIgnoreCase(name)).thenReturn(false);

        brandBusinessRules.checkIfBrandExistsByName(name);

        verify(brandRepository, times(1)).existsByNameIgnoreCase(name);
    }

    @Test
    void shouldThrowEntityAlreadyExistsException_whenBrandNotExistsByName() {
        String name = "BMW";

        when(brandRepository.existsByNameIgnoreCase(name)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> brandBusinessRules.checkIfBrandExistsByName(name));

        verify(brandRepository, times(1)).existsByNameIgnoreCase(name);
    }
}
