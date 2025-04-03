package org.springdemo.springproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.springproject.entity.User;
import org.springdemo.springproject.service.LogApiService;
import org.springdemo.springproject.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
        } else {
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

        // Extract user details from the token
        String username = jwtUtil.extractUsername(token);
        log.info("Extracted username from token: {}", username);

        // Load the user entity
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        log.info("UserDetailsService returned: {}", userDetails.getClass().getSimpleName());

        // Check if userDetails is actually a User instance
        if (!(userDetails instanceof User)) {
            log.error("UserDetails is not an instance of User! Found: " + userDetails.getClass().getSimpleName());
        }

        User user = (User) userDetails; // Safe casting now

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

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
