package org.springdemo.springproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.entity.User;
import org.springdemo.springproject.util.AuthResponse;
import org.springdemo.springproject.service.AuthServiceImpl;
import org.springdemo.springproject.dto.UserDTO;
import org.springdemo.springproject.dto.UserLoginDTO;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import static org.springdemo.springproject.util.Constants.OK;
import static org.springdemo.springproject.util.Constants.TOKEN_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API for register or login.Accessible without a token")
public class AuthenticationController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody @Valid UserDTO userDTO) {
        User user = authServiceImpl.register(userDTO);
        return ApiResponse.map(user, OK, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody UserLoginDTO userloginDTO) {
        AuthResponse token = authServiceImpl.login(userloginDTO);
        return ApiResponse.map(token, TOKEN_SUCCESS, HttpStatus.OK);

    }
}

