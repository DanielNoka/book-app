package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.UserDTO;
import org.springdemo.springproject.dto.UserLoginDTO;
import org.springdemo.springproject.dto.UserUpdateDTO;
import org.springdemo.springproject.enums.Role;
import org.springdemo.springproject.entity.User;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.security.JwtUtil;
import org.springdemo.springproject.util.AuthResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springdemo.springproject.repository.UserRepository;


@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    public User register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new EntityNotFoundException("This username is already taken!");
        }
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new EntityNotFoundException("This email is already taken!");
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));
        userRepository.save(user);
        log.info("User is saved : {} ", user.getUsername());
        return user;
    }

    public AuthResponse login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User with username "+userLoginDTO.getUsername()+" not found"));

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials please try again");
        }

        //Generate Access Token
        String accessToken = jwtUtil.generateToken(user);

        // Generate JWT refresh token
        String refreshToken = jwtUtil.generateRefreshToken(user);

        String tokenExpiration = "30 minutes";
        String refreshTokenExpiration = "60 minutes";

        log.debug("Username {} and  Token: {}", userLoginDTO.getUsername(),accessToken);

        return new AuthResponse(accessToken,refreshToken,tokenExpiration,refreshTokenExpiration);
    }

    public User updateProfile(Long loggedInUserId, UserUpdateDTO userUpdateDTO) {
        // Find the user by their ID (which is extracted from JWT)
        User user = userRepository.findById(loggedInUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        //check if the new email is used
        if (userRepository.existsByEmail(userUpdateDTO.getEmail())) {
            throw new EntityNotFoundException("This email is already in use");
        }
        // Update user details based on the provided userUpdateDTO
        if (userUpdateDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }
        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getUsername() != null) {
            user.setUsername(userUpdateDTO.getUsername());
        }

        // Save the updated user details
        return userRepository.save(user);
    }

    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


}
