package com.ahmed.simpleBank.controller;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public String ping() {
        return "SimpleBank web service is alive ata " + LocalDateTime.now();
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> queryForAllUsers() {
        // call on the business service for the data
        List<User> users = service.findAllUsers();

        // Return the response: OK or NoContent
        return returnNoContentIfEmptyOrNull(users);
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

//    @GetMapping("/getUserById")
//    public ResponseEntity<User> queryForUserById(@RequestParam("id") Long id) {
//        try {
//            service.getUserById(id);
//        } catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /********************************** Utility Methods **********************************************/

    private <T> ResponseEntity<List<T>> returnNoContentIfEmptyOrNull(List<T> list) {
        ResponseEntity<List<T>> result;

        if (Objects.isNull(list) || list.isEmpty()) {
            result = ResponseEntity.noContent().build();
        }
        else {
            result = ResponseEntity.ok(list);
        }
        return result;
    }

}
