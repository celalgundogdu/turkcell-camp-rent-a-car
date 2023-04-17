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
public class GetAllInvoicesResponse {

    private int id;
    private String cardHolder;
    private String plate;
    private double dailyPrice;
    private double totalPrice;
    private int rentedForDays;
    private LocalDateTime rentedAt;
}
