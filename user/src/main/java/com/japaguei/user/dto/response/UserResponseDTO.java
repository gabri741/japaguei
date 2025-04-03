package com.japaguei.user.dto.response;

import com.japaguei.user.domain.model.User;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String email,
        String nome,
        String cpf,
        String cnpj
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getNome(),
                user.getCpf(),
                user.getCnpj()
        );
    }
}

