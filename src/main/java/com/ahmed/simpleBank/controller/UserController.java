package com.ahmed.simpleBank.controller;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.dto.UserDTO;
import com.ahmed.simpleBank.service.UserServiceImp;
import com.ahmed.simpleBank.utils.Validation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.ahmed.simpleBank.utils.Validation.validateDTOForCreate;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserServiceImp service;

    public UserController(UserServiceImp service) {
        this.service = service;
    }

    @GetMapping(value = "ping",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public String ping() {
        return "SimpleBank web service is alive ata " + LocalDateTime.now();
    }

    @GetMapping(value = "users", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.findAllUsers();
        if (users == null || users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "users/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
        User user = service.findUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping(value = "users", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DatabaseRequestResult> createNew(@RequestBody UserDTO userDTO) {
        validateDTOForCreate(userDTO);
        DatabaseRequestResult result = service.addUser(userDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "users/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<DatabaseRequestResult> updateUser(
            @PathVariable("id") UUID id,
            @RequestBody UserDTO userDTO) {

        userDTO.setUserId(id);  // making sure the DTO knows its id
        DatabaseRequestResult result = service.updateUser(userDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "users/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DatabaseRequestResult> deleteUser(@PathVariable("id") UUID id) {
        DatabaseRequestResult result = service.deleteUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /********************************** Utility Methods **********************************************/

}
