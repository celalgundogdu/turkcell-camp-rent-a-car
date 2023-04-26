package com.turkcellcamp.rentacar.common.utils.annotations;

import com.turkcellcamp.rentacar.common.constants.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotFutureYearValidator.class})
public @interface NotFutureYear {
    String message() default Messages.Car.MODEL_YEAR_NOT_VALID;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
