package edu.depaul.cdm.demonhub.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;

    @NotEmpty(message = "Username is rquired")
    @Size(min = 2, max = 20, message = "Username must be at least 2 characters")
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;
    
    private UserRole userRole;

    private byte[] image;
}
