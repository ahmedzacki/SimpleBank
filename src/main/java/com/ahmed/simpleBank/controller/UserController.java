package com.ahmed.simpleBank.controller;

import com.ahmed.simpleBank.business.User;
import com.ahmed.simpleBank.dto.UserDTO;
import com.ahmed.simpleBank.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.ahmed.simpleBank.utils.Validation.validateUserDTO;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserServiceImp service;

    @GetMapping(value = "/ping",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public String ping() {
        return "SimpleBank web service is alive ata " + LocalDateTime.now();
    }

    @GetMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<User>> queryForAllUsers() {
        List<User> users = service.findAllUsers();
        return returnNoContentIfEmptyOrNull(users);
    }

    // POST endpoint to insert a new user
    @PostMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DatabaseRequestResult> createUser(@RequestBody UserDTO userDTO) {
        validateUserDTO(userDTO);
        DatabaseRequestResult result = service.addUser(userDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
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
