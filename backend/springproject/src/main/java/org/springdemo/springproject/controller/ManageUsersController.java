package org.springdemo.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.entity.User;
import org.springdemo.springproject.service.ManageUsers;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springdemo.springproject.util.Constants.OK;

@RestController
@RequestMapping("/manage_users")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "This API is accessed only by ADMINS for managing users")
public class ManageUsersController {

    private final ManageUsers manageUser;

    @GetMapping("/users")
    @Operation(summary = "Retrieves all users with type USER")
    public ApiResponse<List<User>> getAllUsers(){
        List<User> users = manageUser.findAll();
        return ApiResponse.map(users, OK, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Used to delete a user")
    public ApiResponse<HttpStatus> getAllUsers(@PathVariable Long id){
        manageUser.deleteUserById(id);
        return ApiResponse.map(null, OK, HttpStatus.OK);
    }

}
