package com.pratiksha.studentregistration.controller;

import com.pratiksha.studentregistration.exception.ResourceNotFoundException;
import com.pratiksha.studentregistration.repository.UserRepository;
import com.pratiksha.studentregistration.service.UserService;
import com.pratiksha.studentregistration.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity <?> getUserById(@PathVariable Long userId){
        Optional<User> optionalUser = userService.getUserById(userId);
        if(optionalUser.isPresent()){
            return ResponseEntity.ok().body(optionalUser.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserById(@PathVariable Long userId, @RequestBody User user){
        try{
            userService.updateUserById(userId, user);
            return ResponseEntity.noContent().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId){
        try{
            userService.deleteUserById(userId);
            return ResponseEntity.noContent().build();
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

