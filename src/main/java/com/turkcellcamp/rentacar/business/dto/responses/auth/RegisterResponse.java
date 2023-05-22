package com.turkcellcamp.rentacar.business.dto.responses.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private String firstName;
    private String lastName;
    private String username;
}
