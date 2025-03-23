package org.springdemo.springproject.config;

import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.exception.CustomAccessDeniedHandler;
import org.springdemo.springproject.exception.CustomAuthenticationEntryPoint;
import org.springdemo.springproject.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springdemo.springproject.util.Constants.ADMIN;
import static org.springdemo.springproject.util.Constants.USER;
@Configuration
@RequiredArgsConstructor
//@EnableMethodSecurity(prePostEnabled = true)  //Used toe enable method-level security (@PreAuthorize)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/**"
    };

    private static final String[] RESOURCE_ENDPOINTS = {"/authors/**", "/categories/**", "/books/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use JWT (stateless)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, RESOURCE_ENDPOINTS).hasAnyAuthority(USER, ADMIN)
                        .requestMatchers(HttpMethod.POST, RESOURCE_ENDPOINTS).hasAnyAuthority(USER, ADMIN)
                        .requestMatchers(HttpMethod.PUT, RESOURCE_ENDPOINTS).hasAnyAuthority(USER, ADMIN)
                        .requestMatchers(HttpMethod.DELETE, RESOURCE_ENDPOINTS).hasAuthority(ADMIN)  // Only ADMIN can DELETE
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(myCustomAccessDeniedHandler()) // Register the custom AccessDeniedHandler
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler myCustomAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
}