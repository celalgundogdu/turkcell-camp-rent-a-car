package com.turkcellcamp.rentacar.business.dto.requests.create;

import com.turkcellcamp.rentacar.common.constants.Regex;
import com.turkcellcamp.rentacar.validation.UniquePlate;
import jakarta.validation.constraints.Max;
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

    @NotNull
    @Min(2010)
    @Max(2023)
    private int modelYear;

    @NotNull
    @UniquePlate
    @Pattern(regexp = Regex.PLATE, message = "Invalid plate pattern")
    private String plate;

    @NotNull
    @Min(0)
    private double dailyPrice;

    @NotNull
    private int modelId;
}
