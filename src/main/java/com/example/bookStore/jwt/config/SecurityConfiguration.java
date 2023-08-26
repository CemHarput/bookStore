package com.example.bookStore.jwt.config;

import com.example.bookStore.jwt.enums.Role;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html/**",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/v1/users/**"
            // other public endpoints of your API may be appended to this array
    };

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


         http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers(AUTH_WHITELIST)
                 .permitAll()
                 .requestMatchers("/api/v1/addBooks").hasAnyRole(Role.ADMIN.name())
                 .requestMatchers(HttpMethod.DELETE,"/api/v1/books/{isbn}").hasAnyRole(Role.ADMIN.name())
                 .requestMatchers(HttpMethod.PUT,"/api/v1/books/{isbn}").hasAnyRole(Role.ADMIN.name())
                 .anyRequest()
                 .authenticated()).sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

         return http.build();
     }
}