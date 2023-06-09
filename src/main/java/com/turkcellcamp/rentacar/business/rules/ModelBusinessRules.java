package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.core.exceptions.EntityAlreadyExistsException;
import com.turkcellcamp.rentacar.core.exceptions.EntityNotFoundException;
import com.turkcellcamp.rentacar.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelBusinessRules {

    private final ModelRepository modelRepository;

    public void checkIfModelExistsById(int id) {
        if (!modelRepository.existsById(id)) {
            throw new EntityNotFoundException(Messages.Model.NOT_EXISTS);
        }
    }

    public void checkIfModelExistsByName(String name) {
        if (modelRepository.existsByNameIgnoreCase(name)) {
            throw new EntityAlreadyExistsException(Messages.Model.ALREADY_EXISTS);
        }
    }
}
