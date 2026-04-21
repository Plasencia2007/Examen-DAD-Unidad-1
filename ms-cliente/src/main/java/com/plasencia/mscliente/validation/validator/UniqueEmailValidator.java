package com.plasencia.mscliente.validation.validator;

import com.plasencia.mscliente.repository.ClienteRepository;
import com.plasencia.mscliente.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final ClienteRepository clienteRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty()) {
            return true;
        }
        return clienteRepository.findByEmail(email).isEmpty();
    }
}
