package com.plasencia.msproducto.validation.annotation;

import com.plasencia.msproducto.validation.validator.StockValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StockValidator.class)
@Documented
public @interface ValidStock {
    String message() default "{validation.producto.stock.invalido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
