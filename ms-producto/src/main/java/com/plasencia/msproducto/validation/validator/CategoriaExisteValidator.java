package com.plasencia.msproducto.validation.validator;

import com.plasencia.msproducto.repository.CategoriaRepository;
import com.plasencia.msproducto.validation.annotation.CategoriaExiste;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoriaExisteValidator implements ConstraintValidator<CategoriaExiste, Long> {

    private final CategoriaRepository categoriaRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Lo cubre @NotNull
        }
        return categoriaRepository.existsById(value);
    }
}
