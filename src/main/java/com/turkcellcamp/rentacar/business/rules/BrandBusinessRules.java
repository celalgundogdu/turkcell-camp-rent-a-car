package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandBusinessRules {

    private final BrandRepository brandRepository;

    public void checkIfBrandExistsById(int id) {
        if (!brandRepository.existsById(id)){
            throw new RuntimeException(Messages.Brand.NOT_EXISTS);
        }
    }

    public void checkIfBrandExistsByName(String name) {
        if (brandRepository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException(Messages.Brand.ALREADY_EXISTS);
        }
    }
}
