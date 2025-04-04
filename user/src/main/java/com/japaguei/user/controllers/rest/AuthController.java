package com.japaguei.user.controllers.rest;


import com.japaguei.user.domain.service.UserService;
import com.japaguei.user.dto.request.LoginRequest;
import com.japaguei.user.dto.request.RegisterRequest;
import com.japaguei.user.service.auth.CustomUserDetailsService;
import com.japaguei.user.service.auth.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j // Habilita logs do Lombok
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Tentativa de login para o email: {}", loginRequest.email());

        try {
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.email(), loginRequest.password()
            );

            authenticationManager.authenticate(authenticationRequest);

            String token = jwtService.generateToken(loginRequest.email());

            log.info("Login bem-sucedido para o usuário: {}", loginRequest.email());

            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException e) {
            log.warn("Falha na autenticação: Email ou senha inválidos para {}", loginRequest.email());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error", "Email ou senha inválidos",
                            "message", "As credenciais fornecidas estão incorretas"
                    ));

        } catch (Exception e) {
            log.error("Erro inesperado no login de {}: {}", loginRequest.email(), e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Erro interno",
                            "message", "Ocorreu um erro inesperado"
                    ));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest) {
        log.info("Tentativa de cadastro para o email: {}", registerRequest.email());

        if (userService.getUserByEmail(registerRequest.email()) != null) {
            log.warn("Cadastro falhou: usuário {} já existe", registerRequest.email());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.password());

        userService.register(registerRequest, encodedPassword);

        log.info("Usuário cadastrado com sucesso: {}", registerRequest.email());

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
    }
}
