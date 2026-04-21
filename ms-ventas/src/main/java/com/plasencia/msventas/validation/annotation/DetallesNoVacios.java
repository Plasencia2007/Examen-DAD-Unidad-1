package com.plasencia.msventas.validation.annotation;

import com.plasencia.msventas.validation.validator.DetallesNoVaciosValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DetallesNoVaciosValidator.class)
@Documented
public @interface DetallesNoVacios {
    String message() default "{validation.venta.detalles.vacio}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
