package com.plasencia.msproducto.validation.validator;

import com.plasencia.msproducto.validation.annotation.ValidPrecio;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class PrecioValidator implements ConstraintValidator<ValidPrecio, BigDecimal> {

    private static final BigDecimal MAX_PRECIO = new BigDecimal("999999999.99");

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Cubierto por @NotNull
        }
        return value.compareTo(BigDecimal.ZERO) > 0 && value.compareTo(MAX_PRECIO) <= 0;
    }
}
