package com.turkcellcamp.rentacar.business.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @NotBlank(message = "Card number can not be blank")
    @Length(min = 16, max = 16, message = "Card number should contain 16 characters")
    private String cardNumber;

    @NotBlank(message = "Card holder can not be blank")
    @Length(min = 5, message = "Card holder should contain at least 5 characters")
    private String cardHolder;

    @NotNull(message = "Expiration year can not bu null")
    @Min(value = 2023, message = "Expiration year invalid")
    private int cardExpirationYear;

    @NotNull
    @Max(value = 12)
    @Min(value = 1)
    private int cardExpirationMonth;

    @NotBlank(message = "Card CVV can not be blank")
    @Length(min = 3, max = 3, message = "Card CVV should contain 3 characters")
    private String cardCvv;
}
