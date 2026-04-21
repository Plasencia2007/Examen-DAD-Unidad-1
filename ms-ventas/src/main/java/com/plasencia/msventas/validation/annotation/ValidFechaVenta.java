package com.plasencia.msventas.validation.annotation;

import com.plasencia.msventas.validation.validator.FechaVentaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FechaVentaValidator.class)
@Documented
public @interface ValidFechaVenta {
    String message() default "{validation.venta.fecha.invalida}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
