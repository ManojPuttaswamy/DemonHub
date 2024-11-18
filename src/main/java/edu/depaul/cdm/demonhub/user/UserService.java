package edu.depaul.cdm.demonhub.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        log.info("Fetching all users from the database");
        List<User> users = userRepository.findAll();
        log.info("Retrieved {} users from the database", users.size());
        return users;
    }

    public Optional<User> getUserById(String id) {
        log.info("Fetching user with ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("User with ID {} found", id);
        } else {
            log.warn("User with ID {} not found", id);
        }
        return user;
    }

    public Optional<User> getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        Optional<User> user = userRepository.findFirstByEmail(email);
        if (user.isPresent()) {
            log.info("User with email {} found", email);
        } else {
            log.warn("User with email {} not found", email);
        }
        return user;
    }

    public User createUser(User user) {
        log.info("Creating new user with email: {}", user.getEmail());
        User savedUser = userRepository.save(user);
        log.info("User with email {} created successfully with ID: {}", savedUser.getEmail(), savedUser.getId());
        return savedUser;
    }

    public User updateUser(String id, User updatedUser) {
        log.info("Request received to update user with ID: {}", id);
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            log.debug("Existing user details: {}", user);

            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setUserRole(updatedUser.getUserRole());
            user.setImage(updatedUser.getImage());

            User savedUser = userRepository.save(user);
            log.info("User with ID {} updated successfully", id);
            return savedUser;
        }
        log.error("User with ID {} not found. Update failed.", id);
        throw new RuntimeException("User not found with id: " + id);
    }

    public void deleteUser(String id) {
        log.info("Request received to delete user with ID: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with ID {} deleted successfully", id);
        } else {
            log.error("User with ID {} not found. Deletion failed.", id);
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
