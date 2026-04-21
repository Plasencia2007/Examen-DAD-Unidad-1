package com.plasencia.msproducto.validation.annotation;

import com.plasencia.msproducto.validation.validator.PrecioValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PrecioValidator.class)
@Documented
public @interface ValidPrecio {
    String message() default "{validation.producto.precio.invalido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
