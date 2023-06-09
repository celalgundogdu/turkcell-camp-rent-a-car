package com.turkcellcamp.rentacar.business.dto.requests.create;

import com.turkcellcamp.rentacar.business.dto.requests.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {

    private double dailyPrice;
    private int rentedForDays;
    private int carId;
    private PaymentRequest paymentRequest;
}
