package com.japaguei.user.dto.request;

import com.japaguei.user.dto.annotation.CpfOuCnpjObrigatorio;
import jakarta.validation.constraints.*;

@CpfOuCnpjObrigatorio
public record RegisterRequest(
        @NotBlank(message = "O email não pode estar em branco")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "A senha não pode estar em branco")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password,

        @NotBlank(message = "O nome não pode estar em branco")
        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
        String nome,

        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos")
        String cpf,

        @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos numéricos")
        String cnpj
) {}