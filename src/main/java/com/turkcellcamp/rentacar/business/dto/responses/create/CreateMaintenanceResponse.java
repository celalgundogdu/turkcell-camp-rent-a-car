package com.turkcellcamp.rentacar.business.dto.responses.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMaintenanceResponse {

    private int id;
    private String description;
    private boolean isCompleted;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int carId;
}
