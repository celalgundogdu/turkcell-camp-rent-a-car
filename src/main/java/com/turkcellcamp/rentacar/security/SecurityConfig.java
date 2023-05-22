package com.turkcellcamp.rentacar.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthFilter =
                new JwtAuthenticationFilter(authenticationManager(authenticationConfiguration), jwtHelper);
        jwtAuthFilter.setFilterProcessesUrl("/api/users/login");

        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/api/users/**").permitAll();
                    auth.requestMatchers("/api/users/login").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/cars/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/brands/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/api/models/**").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE, "/api/cars/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .addFilter(jwtAuthFilter)
                .addFilterBefore(new JwtTokenVerifier(jwtHelper, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}
