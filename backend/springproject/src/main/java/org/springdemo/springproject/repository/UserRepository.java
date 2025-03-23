package org.springdemo.springproject.repository;

import org.springdemo.springproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u.email FROM User u")
    List<String> findAllEmails();
}