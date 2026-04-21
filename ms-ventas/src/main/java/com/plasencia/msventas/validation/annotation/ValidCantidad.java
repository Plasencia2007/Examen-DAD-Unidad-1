package com.plasencia.msventas.validation.annotation;

import com.plasencia.msventas.validation.validator.CantidadValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CantidadValidator.class)
@Documented
public @interface ValidCantidad {
    String message() default "{validation.detalle.cantidad.invalida}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
