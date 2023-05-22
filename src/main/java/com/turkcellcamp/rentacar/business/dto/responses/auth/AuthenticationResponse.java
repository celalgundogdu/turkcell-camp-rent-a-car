package com.turkcellcamp.rentacar.business.dto.responses.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String firstName;
    private String lastName;
    private String username;
    private String token;
}
