package com.turkcellcamp.rentacar.validation;

import com.turkcellcamp.rentacar.common.constants.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniquePlateValidator.class})
public @interface UniquePlate {

    String message() default Messages.Car.DUPLICATE_PLATE;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
