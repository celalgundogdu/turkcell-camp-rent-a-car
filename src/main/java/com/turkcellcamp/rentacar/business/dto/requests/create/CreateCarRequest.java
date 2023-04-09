package com.turkcellcamp.rentacar.business.dto.requests.create;

import com.turkcellcamp.rentacar.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

    private int modelYear;
    private String plate;
    private double dailyPrice;
    private int modelId;
}
