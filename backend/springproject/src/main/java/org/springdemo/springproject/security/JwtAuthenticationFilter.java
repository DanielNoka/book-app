package org.springdemo.springproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.springproject.service.LogApiService;
import org.springdemo.springproject.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import static org.springdemo.springproject.util.Constants.FAIL;
import static org.springdemo.springproject.util.Constants.INVALID_TOKEN;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final LogApiService logApiService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        String token = extractBearerToken(request);

        if (token != null && !token.isBlank()) {
            try {
                Authentication authentication = getAuthenticationFromToken(token, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException ex) {
                log.warn(INVALID_TOKEN);
                handleException(response, INVALID_TOKEN, HttpStatus.UNAUTHORIZED.value());

                // Log the invalid token attempt asynchronously for performance
                logApiService.saveLog(
                        request.getMethod(),
                        request.getRequestURI(),
                        String.valueOf(HttpServletResponse.SC_UNAUTHORIZED),
                        0L,
                        INVALID_TOKEN,
                        token
                );

                return;
            } catch (Exception ex) {
                log.error("Exception occurred while processing request");
                handleException(response, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
                return;
            }
        }else{
            log.warn("No token found");
        }
        chain.doFilter(request, response);
    }

    private String extractBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private Authentication getAuthenticationFromToken(String token, HttpServletRequest request) {

            if (!jwtUtil.validateToken(token)) {
                throw new JwtException("Token validation failed");
            }

            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            return authToken;
    }

    private void handleException(HttpServletResponse response, String message, int statusCode) throws IOException {

        ErrorResponse errorResponse = new ErrorResponse(FAIL, message, statusCode);
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
