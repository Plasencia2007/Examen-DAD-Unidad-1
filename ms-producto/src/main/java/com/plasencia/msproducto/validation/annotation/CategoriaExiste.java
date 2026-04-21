package com.plasencia.msproducto.validation.annotation;

import com.plasencia.msproducto.validation.validator.CategoriaExisteValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoriaExisteValidator.class)
@Documented
public @interface CategoriaExiste {
    String message() default "{validation.producto.categoria.notfound}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
