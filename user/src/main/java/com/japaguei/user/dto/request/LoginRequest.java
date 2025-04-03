package com.japaguei.user.dto.request;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank(message = "O email não pode estar em branco")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "A senha não pode estar em branco")
        String password
) {}