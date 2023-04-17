package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.entities.enums.State;
import com.turkcellcamp.rentacar.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MaintenanceBusinessRules {

    private final MaintenanceRepository maintenanceRepository;

    public void checkIfMaintenanceExistsById(int id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new RuntimeException(Messages.Maintenance.NOT_EXISTS);
        }
    }

    public void checkIfCarUnderMaintenance(int carId) {
        if (maintenanceRepository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            throw new RuntimeException(Messages.Maintenance.CAR_ALREADY_UNDER_MAINTENANCE);
        }
    }

    public void checkIfCarIsNotUnderMaintenance(int carId) {
        if (!maintenanceRepository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            throw new RuntimeException(Messages.Maintenance.CAR_NOT_UNDER_MAINTENANCE);
        }
    }

    public void checkCarAvailabilityForMaintenance(State state) {
        if (state.equals(State.RENTED)) {
            throw new RuntimeException(Messages.Maintenance.CAR_RENTED);
        }
    }
}
