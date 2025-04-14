package com.ahmed.simpleBank.controller;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.service.SimpleBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class SimpleBankController {

    @Autowired
    private SimpleBankService service;

    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ping() {
        return "SimpleBank web service is alive ata " + LocalDateTime.now();
    }

    @GetMapping(value = "/getAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        // Fetch all users from the database via the DAO
        return service.findAllUsers();
    }

    // POST endpoint to insert a new user
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            // Pass the user object to the service layer for insertion
            service.addUser(user);
            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle any exception that occurs during the user insertion
            return new ResponseEntity<>("Failed to add user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
