package fr.fms.service;

import fr.fms.entities.Role;
import fr.fms.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String email, String role);

    User createManager(User newManager);

    User findUserByUsername(String username);


    User findUserByIdAndRole(Long id, String role);

    List<User> getUsersWithManagerRole();

    Role findByRole(String roleName);

    Optional<Role> findById(Long id);

    ResponseEntity<List<User>> listUsers();
}
