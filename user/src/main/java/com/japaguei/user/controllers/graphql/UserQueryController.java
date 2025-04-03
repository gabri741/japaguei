package com.japaguei.user.controllers.graphql;

import com.japaguei.user.domain.model.User;
import com.japaguei.user.domain.service.UserService;
import com.japaguei.user.dto.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class UserQueryController {

    private final UserService userService;

    @QueryMapping
    public UserResponseDTO getUserByEmail(@Argument String email) {
        return userService.getUserByEmail(email);
    }

    @QueryMapping(name = "getUser")
    public UserResponseDTO getUserById(@Argument String uuid) {
        return userService.getUser(UUID.fromString(uuid));
    }

    @QueryMapping(name = "listUsers")
    public List<User> findAllUsers() {
        return userService.listUsers();
    }

}
