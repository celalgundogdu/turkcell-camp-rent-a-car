package com.turkcellcamp.rentacar.business.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {

    private double dailyPrice;
    private int rentedForDays;
    private LocalDateTime startDate;
    private int carId;
}
