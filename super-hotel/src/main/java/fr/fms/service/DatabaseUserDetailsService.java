package fr.fms.service;

import fr.fms.dao.UserRepository;
import fr.fms.entities.Role;
import fr.fms.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;


    private static final Logger logger = LoggerFactory.getLogger(DatabaseUserDetailsService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        logger.info("Mot de passe en base: {}", user.getPassword());

        logger.info("Tentative de chargement de l'utilisateur avec l'email: {}", username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found" + username);
        }
        List<String> roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toList());

        logger.info("Utilisateur trouvé: {} avec rôles: {}", user.getUsername(), roles);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
    }

}
