package com.example.crm.controller;

import com.example.crm.entity.User;
import com.example.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Tag(name = "User", description = "User Management APIs")
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Create a new user",
            description = "Save information of a new user with: firstname, lastname, username, phone number, password, birthday and gender." +
                    "The response is all about user include id, code, password encoded. "
    )
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }


    @Operation(
            summary = "List all users",
            description = "Find all users with whole information."
    )
    @GetMapping("/user")
    public List<User> findAllUser() {
        return userService.findAllUsers();
    }


    @Operation(
            summary = "Retrieve a user by id",
            description = "Get information about a user by specifying its id. The response is User object with id, code, firstname, lastname, fullname,..."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/user/{id}")
    public Optional<User> findById(@PathVariable("id") String id){
        return userService.findUserById(id);
    }


    @Operation(
            summary = "Update information for a user accroding id",
            description = "Update user with firstname, lastname, phone number, birthday, gender by specifying its id." +
                    "The response is all about a user."
    )
    @PatchMapping("/user/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") String id){
        return userService.updateUser(user, id);
    }

    @Operation(
            summary = "Delete a user according id",
            description = "Delete user by specifying its id."
    )
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}

