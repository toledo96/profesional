package com.programming.techie.gateway.controller;

import com.programming.techie.gateway.model.User;
import com.programming.techie.gateway.service.FakeUserService;
import com.programming.techie.gateway.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/token")
    public String getToken() {
        return JwtUtil.generateToken("test",List.of("USER"));
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) {

        User user = FakeUserService.getUser(request.getUsername());

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        List<String> roles = user.getRoles()
                .stream()
                .map(role -> role.name())
                .collect(Collectors.toList());

        return JwtUtil.generateToken(user.getUsername(), roles);
    }

}
