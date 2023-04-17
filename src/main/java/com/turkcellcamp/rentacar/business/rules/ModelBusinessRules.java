package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelBusinessRules {

    private final ModelRepository modelRepository;

    public void checkIfModelExistsById(int id) {
        if (!modelRepository.existsById(id)) {
            throw new RuntimeException(Messages.Model.NOT_EXISTS);
        }
    }

    public void checkIfModelExistsByName(String name) {
        if (modelRepository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException(Messages.Model.ALREADY_EXISTS);
        }
    }
}
