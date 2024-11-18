package edu.depaul.cdm.demonhub.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for managing users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @Operation(summary = "Get all users", description = "Fetches all users in the system")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        log.info("Retrieved {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Fetches a user by their unique ID")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        log.info("Fetching user with ID: {}", id);
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            log.info("User with ID {} found", id);
            return ResponseEntity.ok(user.get());
        } else {
            log.warn("User with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Fetches a user by their email address")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        log.info("Fetching user with email: {}", email);
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            log.info("User with email {} found", email);
            return ResponseEntity.ok(user.get());
        } else {
            log.warn("User with email {} not found", email);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    @Operation(summary = "Create a new user", description = "Adds a new user to the system")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Request received to create a new user: {}", user.getEmail());
        User createdUser = userService.createUser(user);
        log.info("User created successfully with ID: {}", createdUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user", description = "Updates an existing user's details by their ID")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        log.info("Request received to update user with ID: {}", id);
        try {
            User user = userService.updateUser(id, updatedUser);
            log.info("User with ID {} updated successfully", id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            log.error("Update failed: User with ID {} not found", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Deletes a user from the system by their ID")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        log.info("Request received to delete user with ID: {}", id);
        try {
            userService.deleteUser(id);
            log.info("User with ID {} deleted successfully", id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            log.error("Delete failed: User with ID {} not found", id, e);
            return ResponseEntity.notFound().build();
        }
    }
}
