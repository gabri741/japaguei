package com.japaguei.user.controllers.rest;


import com.japaguei.user.domain.service.UserService;
import com.japaguei.user.dto.request.LoginRequest;
import com.japaguei.user.dto.request.RegisterRequest;
import com.japaguei.user.service.auth.CustomUserDetailsService;
import com.japaguei.user.service.auth.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
        try {
            Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.email(), loginRequest.password()
            );

            // Tenta autenticar
            authenticationManager.authenticate(authenticationRequest);

            // Se autenticar, gera o token
            String token = jwtService.generateToken(loginRequest.email());

            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "error", "Email ou senha inválidos",
                            "message", "As credenciais fornecidas estão incorretas"
                    ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Erro interno",
                            "message", e.getMessage()
                    ));
        }
    }



    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest registerRequest) {

        if (userService.getUserByEmail(registerRequest.email()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.password());

        userService.register(registerRequest, encodedPassword);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado com sucesso!");
    }

}
