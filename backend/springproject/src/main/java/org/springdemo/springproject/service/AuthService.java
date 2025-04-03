package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.UserDTO;
import org.springdemo.springproject.dto.UserLoginDTO;
import org.springdemo.springproject.dto.UserUpdateDTO;
import org.springdemo.springproject.entity.User;
import org.springdemo.springproject.util.AuthResponse;

public interface AuthService {

    User register(UserDTO userDTO);
    AuthResponse login(UserLoginDTO userLoginDTO);
    User updateProfile(Long id, UserUpdateDTO updateRequest);
    void updatePassword(String email, String newPassword);

}
