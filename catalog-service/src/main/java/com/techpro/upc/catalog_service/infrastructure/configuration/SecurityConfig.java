package com.techpro.upc.catalog_service.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita CSRF para APIs REST
                .authorizeHttpRequests(authorize -> authorize
                        // PERMITIR TODO EL CATÁLOGO PÚBLICAMENTE
                        .requestMatchers("/api/v1/catalog/**").permitAll()
                        // El resto puede requerir autenticación si lo deseas
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
