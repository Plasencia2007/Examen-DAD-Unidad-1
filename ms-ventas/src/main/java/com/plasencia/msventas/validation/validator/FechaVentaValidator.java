package com.plasencia.msventas.validation.validator;

import com.plasencia.msventas.validation.annotation.ValidFechaVenta;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FechaVentaValidator implements ConstraintValidator<ValidFechaVenta, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) return false;
        LocalDateTime now = LocalDateTime.now();
        if (value.isAfter(now.plusMinutes(5))) return false;
        if (value.isBefore(now.minusYears(1))) return false;
        return true;
    }
}
