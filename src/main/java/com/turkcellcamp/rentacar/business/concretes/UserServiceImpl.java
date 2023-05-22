package com.turkcellcamp.rentacar.business.concretes;

import com.turkcellcamp.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.business.dto.requests.auth.AuthenticationRequest;
import com.turkcellcamp.rentacar.business.dto.requests.auth.RegisterRequest;
import com.turkcellcamp.rentacar.business.dto.responses.auth.AuthenticationResponse;
import com.turkcellcamp.rentacar.business.dto.responses.auth.RegisterResponse;
import com.turkcellcamp.rentacar.entities.User;
import com.turkcellcamp.rentacar.entities.enums.Role;
import com.turkcellcamp.rentacar.repository.UserRepository;
import com.turkcellcamp.rentacar.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User createdUser = userRepository.save(user);
        RegisterResponse response = new RegisterResponse(
                createdUser.getFirstName(), createdUser.getLastName(), createdUser.getUsername());

        return response;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtHelper.generateToken(user.getUsername());
        AuthenticationResponse response = new AuthenticationResponse(
                user.getFirstName(), user.getLastName(), user.getUsername(), token);

        return response;
    }

}
