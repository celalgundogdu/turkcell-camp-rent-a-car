package com.turkcellcamp.rentacar.business.dto.requests.update;

import com.turkcellcamp.rentacar.business.dto.requests.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentRequest extends PaymentRequest {

    private double balance;
}
