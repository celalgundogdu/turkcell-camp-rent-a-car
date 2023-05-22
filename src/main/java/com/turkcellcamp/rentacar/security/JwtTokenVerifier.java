package com.turkcellcamp.rentacar.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                DecodedJWT decodedJWT = jwtHelper.decodeToken(token);
                String username = decodedJWT.getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(),
                                null,
                                userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request,response);
            } catch (JWTVerificationException exception) {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(401);
                Map<String,String> responseData = new HashMap<>();
                responseData.put("message",exception.getMessage());
                responseData.put("timestamp", LocalDateTime.now().toString());
                new ObjectMapper().writeValue(response.getOutputStream(), responseData);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}