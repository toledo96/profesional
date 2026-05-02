package com.programming.techie.gateway.service;

import com.programming.techie.gateway.model.Role;
import com.programming.techie.gateway.model.User;

import java.util.List;

public class FakeUserService {
    public static User getUser(String username) {

        if (username.equals("admin")) {
            return User.builder()
                    .username("admin")
                    .password("123")
                    .roles(List.of(Role.ADMIN))
                    .build();
        }

        return User.builder()
                .username("user")
                .password("123")
                .roles(List.of(Role.USER))
                .build();
    }
}
