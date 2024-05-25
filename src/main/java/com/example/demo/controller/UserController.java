package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users") // localhost:8080/demo
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping  // GET localhost:8080/users
    public ResponseEntity<User[]>getUsers() {
        return ResponseEntity.ok(userService.getUsers().get());
    }

    @PostMapping // POST localhost:8080/users
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println(error.getField() + " - " + error.getRejectedValue());
            }
            throw new RuntimeException("Bad Request!");
        }
        return ResponseEntity.ok(userService.createNewUser(user).get());
    }

    @PutMapping("/{id}") // PUT localhost:8080/users/1
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUserById(id, user).get());
    }

    @DeleteMapping("/{id}") // DELETE localhost:8080/users/1
    public ResponseEntity<Boolean> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
}
