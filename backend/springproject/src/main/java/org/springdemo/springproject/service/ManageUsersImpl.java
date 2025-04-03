package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.enums.Role;
import org.springdemo.springproject.entity.User;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageUsersImpl implements ManageUsers {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findUsersByRole(Role.USER);
    }

    @Override
    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
