package com.plasencia.msventas.validation.validator;

import com.plasencia.msventas.validation.annotation.ValidCantidad;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CantidadValidator implements ConstraintValidator<ValidCantidad, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value > 0 && value <= 10000;
    }
}
