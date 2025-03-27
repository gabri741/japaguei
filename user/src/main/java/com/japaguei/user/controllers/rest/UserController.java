package com.japaguei.user.controllers.rest;


import com.japaguei.user.domain.model.User;
import com.japaguei.user.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;


    @PostMapping("/salvar")
    public User salvarUser(@RequestBody User user) {
        return userRepository.save(user);
    }


}
