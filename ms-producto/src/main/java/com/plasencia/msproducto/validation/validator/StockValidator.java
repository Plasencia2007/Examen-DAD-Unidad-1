package com.plasencia.msproducto.validation.validator;

import com.plasencia.msproducto.validation.annotation.ValidStock;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StockValidator implements ConstraintValidator<ValidStock, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Cubierto por @NotNull
        }
        return value >= 0 && value <= 999999;
    }
}
