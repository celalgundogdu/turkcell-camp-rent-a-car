package com.turkcellcamp.rentacar.business.dto.responses.create;

import com.turkcellcamp.rentacar.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarResponse {

    private int id;
    private int modelYear;
    private String plate;
    private State state;
    private double dailyPrice;
}
