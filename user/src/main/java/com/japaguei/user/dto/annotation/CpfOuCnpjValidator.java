package com.japaguei.user.dto.annotation;

import com.japaguei.user.dto.request.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfOuCnpjValidator implements ConstraintValidator<CpfOuCnpjObrigatorio, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest dto, ConstraintValidatorContext context) {
        boolean cpfPreenchido = dto.cpf() != null && !dto.cpf().trim().isEmpty();
        boolean cnpjPreenchido = dto.cnpj() != null && !dto.cnpj().trim().isEmpty();

        return cpfPreenchido ^ cnpjPreenchido;
    }
}
