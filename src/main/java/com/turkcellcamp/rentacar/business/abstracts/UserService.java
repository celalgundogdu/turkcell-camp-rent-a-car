package com.turkcellcamp.rentacar.business.abstracts;

import com.turkcellcamp.rentacar.business.dto.requests.auth.AuthenticationRequest;
import com.turkcellcamp.rentacar.business.dto.requests.auth.RegisterRequest;
import com.turkcellcamp.rentacar.business.dto.responses.auth.AuthenticationResponse;
import com.turkcellcamp.rentacar.business.dto.responses.auth.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
