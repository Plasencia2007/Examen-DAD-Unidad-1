package com.plasencia.msventas.validation.validator;

import com.plasencia.msventas.dto.DetalleRequestDTO;
import com.plasencia.msventas.validation.annotation.DetallesNoVacios;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Objects;

public class DetallesNoVaciosValidator implements ConstraintValidator<DetallesNoVacios, List<DetalleRequestDTO>> {

    @Override
    public boolean isValid(List<DetalleRequestDTO> detalles, ConstraintValidatorContext context) {
        if (detalles == null || detalles.isEmpty()) return false;
        long distinct = detalles.stream()
                .map(DetalleRequestDTO::getProductoId)
                .filter(Objects::nonNull)
                .distinct()
                .count();
        return distinct == detalles.size();
    }
}
