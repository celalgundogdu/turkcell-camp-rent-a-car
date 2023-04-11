package com.turkcellcamp.rentacar.business.dto.requests.create;

import com.turkcellcamp.rentacar.business.dto.requests.PaymentRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest extends PaymentRequest {

    @NotNull
    @Min(value = 0)
    private double balance;
}
