package com.plasencia.mscliente.validation.validator;

import com.plasencia.mscliente.validation.annotation.ValidTelefono;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefonoValidator implements ConstraintValidator<ValidTelefono, String> {

    private static final String REGEX_PERU = "^9\\d{8}$";
    private static final String REGEX_INTL = "^(\\+51)?9\\d{8}$";

    @Override
    public boolean isValid(String telefono, ConstraintValidatorContext context) {
        if (telefono == null || telefono.isEmpty()) {
            return true;
        }
        return telefono.matches(REGEX_PERU) || telefono.matches(REGEX_INTL);
    }
}
