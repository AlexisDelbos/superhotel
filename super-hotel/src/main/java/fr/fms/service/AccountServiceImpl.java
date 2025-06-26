package fr.fms.service;

import fr.fms.dao.RoleRepository;
import fr.fms.dao.UserRepository;
import fr.fms.entities.Role;
import fr.fms.entities.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) {
        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        log.info("Saving user: " + user);
        return userRepository.save(user);
    }

    @Override
    public User createManager(User user){
        Role roleManager = roleRepository.findByRole("MANAGER");
        user.getRoles().add(roleManager);
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role: " + role);
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        Role role1 = roleRepository.findByRole(role);
        User user = userRepository.findByUsername(username);
        if (role1 != null && user != null) {
            user.getRoles().add(role1);
            userRepository.save(user);
            log.info("Added role: " + role + " to user: " + username);
        } else {
            log.error("User or Role not found! User: " + username + ", Role: " + role);
        }
    }



    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    @Override
    public User findUserByIdAndRole(Long id, String role){
        return userRepository.findByIdAndRoles_Role(id, role);
    }

    @Override
    public List<User> getUsersWithManagerRole() {
        return userRepository.findByRoles_Role("MANAGER");
    }

    @Override
    public Role findByRole(String roleName) {
        return roleRepository.findByRole(roleName);
    }


    @Override
    public Optional<Role> findById(Long id){
        return roleRepository.findById(id);
    }

    @Override
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}

