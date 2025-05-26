package com.ahmed.simpleBank.controller;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.service.CommonService;
import com.ahmed.simpleBank.service.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    private final UserServiceImp service;
    private final CommonService commonService;

    public UserController(UserServiceImp service, CommonService commonService) {
        this.service = service;
        this.commonService = commonService;
    }

    @GetMapping(value = "/ping",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public String ping() {
        return "SimpleBank web service is alive at " + LocalDateTime.now();
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.findAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
        User user = commonService.findUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<DatabaseRequestResult> updateUser(
            @PathVariable("id") UUID id,
            @RequestBody User user) {

        user.setUserId(id);  // making sure the DTO knows its id
        DatabaseRequestResult result = service.updateUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DatabaseRequestResult> deleteUser(@PathVariable("id") UUID id) {
        DatabaseRequestResult result = service.deleteUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
