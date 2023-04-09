package com.turkcellcamp.rentacar.api.controllers;

import com.turkcellcamp.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.business.abstracts.MaintenanceService;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreateCarRequest;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreateMaintenanceRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdateCarRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdateMaintenanceRequest;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreateCarResponse;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreateMaintenanceResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllCarsResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllMaintenancesResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetCarResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetMaintenanceResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdateCarResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdateMaintenanceResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@AllArgsConstructor
public class MaintenancesController {

    private final MaintenanceService maintenanceService;

    @GetMapping
    public List<GetAllMaintenancesResponse> getAll() {
        return maintenanceService.getAll();
    }

    @GetMapping("/{id}")
    public GetMaintenanceResponse getById(@PathVariable int id) {
        return maintenanceService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateMaintenanceResponse add(@RequestBody CreateMaintenanceRequest request) {
        return maintenanceService.add(request);
    }

    @PutMapping("/{id}")
    public UpdateMaintenanceResponse update(@PathVariable int id, @RequestBody UpdateMaintenanceRequest request) {
        return maintenanceService.update(id, request);
    }

    @PutMapping("/return")
    public GetMaintenanceResponse returnCarFromMaintenance(@RequestParam int carId) {
        return maintenanceService.returnCarFromMaintenance(carId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        maintenanceService.delete(id);
    }
}
