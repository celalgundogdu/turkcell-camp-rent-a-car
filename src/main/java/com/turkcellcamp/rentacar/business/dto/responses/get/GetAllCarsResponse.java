package com.turkcellcamp.rentacar.business.dto.responses.get;

import com.turkcellcamp.rentacar.entities.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCarsResponse {

    private int id;
    private int modelYear;
    private String plate;
    private State state;
    private double dailyPrice;
    private String modelName;
}
