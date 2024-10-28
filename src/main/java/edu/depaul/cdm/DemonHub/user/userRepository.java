package edu.depaul.cdm.DemonHub.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;


@Repository
public interface userRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);
    
}
