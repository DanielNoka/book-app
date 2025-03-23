package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.UserDTO;
import org.springdemo.springproject.dto.UserLoginDTO;
import org.springdemo.springproject.entity.Role;
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
        String accessToken = jwtUtil.generateToken(userLoginDTO.getUsername(), user.getRole().name());

        // Generate JWT refresh token
        String refreshToken = jwtUtil.generateRefreshToken(userLoginDTO.getUsername(), user.getRole().name());

        String tokenExpiration = "30 minutes";
        String refreshTokenExpiration = "7 days";

        log.debug("Username {} and  Token: {}", userLoginDTO.getUsername(),accessToken);

        return new AuthResponse(accessToken,refreshToken,tokenExpiration,refreshTokenExpiration);
    }
}
