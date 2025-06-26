package fr.fms.web;

import fr.fms.dao.RoleRepository;
import fr.fms.entities.City;
import fr.fms.entities.Hotel;
import fr.fms.entities.Role;
import fr.fms.entities.User;
import fr.fms.service.AccountServiceImpl;
import fr.fms.service.ImplHotelService;
import jakarta.transaction.Transactional;
import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@Transactional
@RequestMapping("/api")
public class ManagerController {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    ImplHotelService hotelService;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/managers")
    public List<User> getUsersWithManagerRole() {
        return accountService.getUsersWithManagerRole();
    }

    @PostMapping("/managers")
    public ResponseEntity<User> addUser(@RequestBody User user) {

        User userSaved = accountService.createManager(user);
        System.out.println("User Saved " + userSaved);
        return ResponseEntity.ok(userSaved);
    }



}

