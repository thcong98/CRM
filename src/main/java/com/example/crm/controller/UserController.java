package com.example.crm.controller;

import com.example.crm.entity.User;
import com.example.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "User", description = "User Management APIs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Find all users",
            description = "List all users with their whole information. Both admin and user role can do it."
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> findAllUser() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }


    @Operation(
            summary = "Retrieve a user by id",
            description = "Get information about a user by specifying its id. The response is User object with id, code, firstname, lastname, fullname,..." +
                    "Both admin and user can do it."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findById (@PathVariable("id") UUID id) {
        return new ResponseEntity<>(userService.findUserById(String.valueOf(id)), HttpStatus.OK);
    }


    @Operation(
            summary = "Update information for a user according id",
            description = "Update user with firstname, lastname, phone number, birthday, gender by specifying its id." +
                    "The response is all about a user." +
                    "Person has Admin role can do it."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") UUID id) {
        return new ResponseEntity<User>(userService.updateUser(user, String.valueOf(id)), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a user according id",
            description = "Delete user by specifying its id." +
                    "Person has Admin role can do it."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id")UUID id) {
        userService.deleteUser(UUID.fromString(String.valueOf(id)));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

