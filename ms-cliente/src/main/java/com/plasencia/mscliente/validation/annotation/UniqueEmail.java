package com.plasencia.mscliente.validation.annotation;

import com.plasencia.mscliente.validation.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {
    String message() default "{validation.cliente.email.unique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
