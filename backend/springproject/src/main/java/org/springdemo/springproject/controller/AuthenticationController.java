package org.springdemo.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.dto.*;
import org.springdemo.springproject.entity.User;
import org.springdemo.springproject.repository.UserRepository;
import org.springdemo.springproject.service.AuthService;
import org.springdemo.springproject.service.OTPService;
import org.springdemo.springproject.util.AuthResponse;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import static org.springdemo.springproject.util.Constants.OK;
import static org.springdemo.springproject.util.Constants.TOKEN_SUCCESS;
import static org.springdemo.springproject.util.Constants.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API for user registration, login, and profile management. " +
        "These endpoints are accessible without a token.")
public class AuthenticationController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final OTPService otpService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "This endpoint allows a user to register by providing their account details.")
    public ApiResponse<User> register(@RequestBody @Valid UserDTO userDTO) {
        User user = authService.register(userDTO);
        return ApiResponse.map(user, OK, HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "This endpoint allows a user to log in and receive authentication tokens for further operations.")
    public ApiResponse<AuthResponse> login(@RequestBody UserLoginDTO userloginDTO) {
        AuthResponse token = authService.login(userloginDTO);
        return ApiResponse.map(token, TOKEN_SUCCESS, HttpStatus.OK);
    }

    @PatchMapping("/updateProfile")
    @Operation(summary = "Update user profile", description = "This endpoint allows an authenticated user to update their profile information.")
    public ApiResponse<User> updateProfile(
            @AuthenticationPrincipal User user, // Extract JWT claims
            @RequestBody @Valid UserUpdateDTO updateRequest){

        Long userId = user.getId();// Extract user ID from JWT
        User updatedUser = authService.updateProfile(userId,updateRequest);
        userRepository.save(updatedUser);
        return ApiResponse.map(updatedUser, OK, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Send OTP for password reset", description = "This endpoint sends a OTP to the user's email which will be used for resetting their password.")
    public ApiResponse<HttpStatus> sendOtp(@RequestParam String email) {
        String otp = otpService.generateOtp(email);
        otpService.sendOtpEmail(email, otp);
        return ApiResponse.map(null,SUCCESS,HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset user password", description = "This endpoint allows a user to reset their password by providing their email, OTP, and new password.")
    public ApiResponse<String> resetPassword(
            @Valid @RequestBody OtpDTO otpDTO) {

        if (!otpService.validateOtp(otpDTO.getEmail(), otpDTO.getOtp())) {
            return ApiResponse.map(otpDTO.getOtp(),"Invalid or expired OTP",HttpStatus.BAD_REQUEST);
        }

        authService.updatePassword(otpDTO.getEmail(), otpDTO.getNewPassword());
        otpService.removeOtp(otpDTO.getEmail());
        return ApiResponse.map(null,SUCCESS,HttpStatus.OK);
    }

}

