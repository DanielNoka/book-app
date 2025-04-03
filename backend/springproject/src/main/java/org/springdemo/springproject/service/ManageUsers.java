package org.springdemo.springproject.service;

import org.springdemo.springproject.entity.User;

import java.util.List;

public interface ManageUsers {

    List<User> findAll();
    void deleteUserById(Long id);

}
