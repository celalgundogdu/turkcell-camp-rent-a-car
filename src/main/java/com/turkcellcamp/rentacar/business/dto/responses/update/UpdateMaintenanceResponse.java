package com.turkcellcamp.rentacar.business.dto.responses.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMaintenanceResponse {

    private int id;
    private String description;
    private int carId;
}
