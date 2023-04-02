package com.turkcellcamp.rentacar.business.dto.requests.update;

import com.turkcellcamp.rentacar.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {

    private int modelYear;
    private String plate;
    private State state;
    private double dailyPrice;
    private int modelId;
}
