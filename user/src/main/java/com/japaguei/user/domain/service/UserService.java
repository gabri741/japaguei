package com.japaguei.user.domain.service;

import com.japaguei.user.domain.model.User;
import com.japaguei.user.domain.repository.UserRepository;
import com.japaguei.user.dto.request.RegisterRequest;
import com.japaguei.user.dto.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO getUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserResponseDTO::fromEntity)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public UserResponseDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserResponseDTO::fromEntity)
                .orElse(null);
    }

    public void register(RegisterRequest registerRequest, String password) {
        User user = new User();
        user.setEmail(registerRequest.email());
        user.setSenha(password);
        user.setNome(registerRequest.nome());
        user.setCpf(registerRequest.cpf());
        user.setCnpj(registerRequest.cnpj());
        userRepository.save(user);
    }



}
