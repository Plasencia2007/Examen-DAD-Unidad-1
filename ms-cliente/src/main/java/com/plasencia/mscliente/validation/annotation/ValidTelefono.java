package com.plasencia.mscliente.validation.annotation;

import com.plasencia.mscliente.validation.validator.TelefonoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelefonoValidator.class)
@Documented
public @interface ValidTelefono {
    String message() default "{validation.cliente.telefono.formato}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
