package com.plasencia.mscliente.validation.annotation;

import com.plasencia.mscliente.validation.validator.NombreApellidoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NombreApellidoValidator.class)
@Documented
public @interface ValidNombreApellido {
    String message() default "{validation.cliente.nombre.formato}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
