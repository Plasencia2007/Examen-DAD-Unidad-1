package com.plasencia.msproducto.validation.annotation;

import com.plasencia.msproducto.validation.validator.UniqueNombreCategoriaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueNombreCategoriaValidator.class)
@Documented
public @interface UniqueNombreCategoria {
    String message() default "{validation.categoria.nombre.unique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
