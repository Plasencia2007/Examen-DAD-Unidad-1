package com.plasencia.msproducto.validation.validator;

import com.plasencia.msproducto.repository.CategoriaRepository;
import com.plasencia.msproducto.validation.annotation.UniqueNombreCategoria;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueNombreCategoriaValidator implements ConstraintValidator<UniqueNombreCategoria, String> {

    private final CategoriaRepository categoriaRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return !categoriaRepository.existsByNombreIgnoreCase(value);
    }
}
