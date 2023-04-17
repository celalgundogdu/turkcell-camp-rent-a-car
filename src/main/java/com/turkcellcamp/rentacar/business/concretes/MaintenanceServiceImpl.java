package com.turkcellcamp.rentacar.business.concretes;

import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.abstracts.MaintenanceService;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreateMaintenanceRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdateMaintenanceRequest;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreateMaintenanceResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllMaintenancesResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetMaintenanceResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdateMaintenanceResponse;
import com.turkcellcamp.rentacar.business.rules.MaintenanceBusinessRules;
import com.turkcellcamp.rentacar.entities.Maintenance;
import com.turkcellcamp.rentacar.entities.enums.State;
import com.turkcellcamp.rentacar.repository.MaintenanceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceBusinessRules rules;
    private final CarService carService;
    private final ModelMapper mapper;

    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        List<GetAllMaintenancesResponse> response = maintenanceList
                .stream()
                .map(maintenance -> mapper.map(maintenance, GetAllMaintenancesResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetMaintenanceResponse getById(int id) {
        rules.checkIfMaintenanceExistsById(id);
        Maintenance maintenance = maintenanceRepository.findById(id).orElseThrow();
        GetMaintenanceResponse response = mapper.map(maintenance, GetMaintenanceResponse.class);
        return response;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(int carId) {
        rules.checkIfCarIsNotUnderMaintenance(carId);
        Maintenance maintenance = maintenanceRepository.findByCarIdAndIsCompletedIsFalse(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());
        Maintenance completedMaintenance = maintenanceRepository.save(maintenance);
        carService.changeState(carId, State.AVAILABLE);
        GetMaintenanceResponse response = mapper.map(completedMaintenance, GetMaintenanceResponse.class);
        return response;
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.checkIfCarUnderMaintenance(request.getCarId());
        rules.checkCarAvailabilityForMaintenance(carService.getById(request.getCarId()).getState());
        Maintenance maintenance = mapper.map(request, Maintenance.class);
        maintenance.setId(0);
        maintenance.setCompleted(false);
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setEndDate(null);
        Maintenance createdMaintenance = maintenanceRepository.save(maintenance);
        carService.changeState(request.getCarId(), State.MAINTENANCE);
        CreateMaintenanceResponse response = mapper.map(createdMaintenance, CreateMaintenanceResponse.class);
        return response;
    }

    @Override
    public UpdateMaintenanceResponse update(int id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExistsById(id);
        rules.checkCarAvailabilityForMaintenance(carService.getById(request.getCarId()).getState());
        Maintenance maintenance = mapper.map(request, Maintenance.class);
        maintenance.setId(id);
        Maintenance updatedMaintenance = maintenanceRepository.save(maintenance);

        UpdateMaintenanceResponse response = mapper.map(updatedMaintenance, UpdateMaintenanceResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        rules.checkIfMaintenanceExistsById(id);
        makeCarAvailableIfIsCompletedFalse(id);
        maintenanceRepository.deleteById(id);
    }

    private void makeCarAvailableIfIsCompletedFalse(int id) {
        int carId = maintenanceRepository.findById(id).get().getCar().getId();
        if (maintenanceRepository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            carService.changeState(carId, State.AVAILABLE);
        }
    }
}
