package com.turkcellcamp.rentacar.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHelper {

    private static final String SECRET_KEY = "792F423F4528482B4D6251655468566D597133743677397A24432646294A404E";
    private static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public DecodedJWT decodeToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        return jwtVerifier.verify(token);
    }
}
