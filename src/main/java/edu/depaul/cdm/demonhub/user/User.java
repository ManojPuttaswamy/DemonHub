package edu.depaul.cdm.demonhub.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;
    private UserRole userRole;

    private byte[] image;
}
