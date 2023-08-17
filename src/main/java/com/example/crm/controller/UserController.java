package com.example.crm.controller;

import com.example.crm.entity.ERole;
import com.example.crm.entity.Role;
import com.example.crm.entity.User;
import com.example.crm.payload.request.SignupRequest;
import com.example.crm.payload.response.MessageResponse;
import com.example.crm.repository.RoleRepository;
import com.example.crm.repository.UserRepository;
import com.example.crm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "User", description = "User Management APIs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @Operation(summary = "Create a new user",
            description = "Save information of a new user with: firstname, lastname, email, username, phone number, password, birthday, gender and role.")
    @PostMapping("/create")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getFirstname(),
                signUpRequest.getLastname(),
                signUpRequest.getLastname() + " " +  signUpRequest.getFirstname(),
                signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                signUpRequest.getPhoneNumber(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getBirthday(),
                signUpRequest.getGender()
        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Operation(
            summary = "Find all users",
            description = "List all users with their whole information.\nadmin role can do it."
    )
    @GetMapping("/all")
    public ResponseEntity<List<User>> findAllUser() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }


    @Operation(
            summary = "Retrieve a user by id",
            description = "Get information about a user by specifying its id.\n The response is User object with id, code, firstname, lastname, fullname,..." +
                    " admin can do it."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findUserById (@PathVariable("id") UUID id) {
        return new ResponseEntity<>(userService.findUserById(String.valueOf(id)), HttpStatus.OK);
    }


    @Operation(
            summary = "Update information for a user according id",
            description = "Update user with firstname, lastname, phone number, birthday, gender by specifying its id.\n" +
                    "The response is all about a user.\n" +
                    "Person has Admin role can do it."
    )
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") UUID id) {
        return new ResponseEntity<User>(userService.updateUser(user, String.valueOf(id)), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a user according id",
            description = "Delete user by specifying its id.\n" +
                    "Person has Admin role can do it."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id")UUID id) {
        userService.deleteUser(UUID.fromString(String.valueOf(id)));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

