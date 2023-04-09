package com.turkcellcamp.rentacar.business.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllRentalsResponse {

    private int id;
    private double dailyPrice;
    private int rentedForDays;
    private LocalDateTime startDate;
    private int carId;
}
