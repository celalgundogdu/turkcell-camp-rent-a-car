package com.turkcellcamp.rentacar.business.dto.requests.create;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.common.constants.Regex;
import com.turkcellcamp.rentacar.common.utils.annotations.NotFutureYear;
import com.turkcellcamp.rentacar.common.utils.annotations.UniquePlate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

    @Min(2010)
    @NotFutureYear
    private int modelYear;

    @NotNull
    @UniquePlate
    @Pattern(regexp = Regex.PLATE, message = Messages.Car.PLATE_NOT_VALID)
    private String plate;

    @Min(0)
    private double dailyPrice;

    @Min(0)
    private int modelId;
}
