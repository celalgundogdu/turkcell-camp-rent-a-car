package com.turkcellcamp.rentacar.api.controllers;

import com.turkcellcamp.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.business.dto.requests.auth.AuthenticationRequest;
import com.turkcellcamp.rentacar.business.dto.requests.auth.RegisterRequest;
import com.turkcellcamp.rentacar.business.dto.responses.auth.AuthenticationResponse;
import com.turkcellcamp.rentacar.business.dto.responses.auth.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
        return userService.authenticate(request);
    }
}
