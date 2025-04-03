package com.japaguei.user.dto.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CpfOuCnpjValidator.class)
@Target(ElementType.TYPE) // Validação a nível de classe
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfOuCnpjObrigatorio {
    String message() default "Preencha apenas CPF ou CNPJ, e um deles é obrigatório.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}