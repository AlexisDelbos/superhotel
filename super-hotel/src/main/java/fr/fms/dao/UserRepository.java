package fr.fms.dao;


import fr.fms.entities.Role;
import fr.fms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);


    List<User> findByRoles_Role(String role);

    User findByIdAndRoles_Role(Long id, String role);



}
