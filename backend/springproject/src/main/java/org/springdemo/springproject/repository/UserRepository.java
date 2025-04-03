package org.springdemo.springproject.repository;

import org.springdemo.springproject.enums.Role;
import org.springdemo.springproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);  // Case insensitive query

    boolean existsByEmail(String email);

    @Query("SELECT u.email FROM User u")
    List<String> findAllEmails();

    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findUsersByRole(@Param("role") Role role);

}