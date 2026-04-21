package com.plasencia.mscliente.validation.validator;

import com.plasencia.mscliente.validation.annotation.ValidNombreApellido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NombreApellidoValidator implements ConstraintValidator<ValidNombreApellido, String> {

    private static final String REGEX_LETTERS = "^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return value.matches(REGEX_LETTERS) && value.trim().length() > 0;
    }
}
