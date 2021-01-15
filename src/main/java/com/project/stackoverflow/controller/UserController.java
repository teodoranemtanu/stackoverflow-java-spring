package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.UserModel;
import com.project.stackoverflow.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getUsers(@RequestParam(required = false) String communityId) {
        List<UserModel> users = userService.getUsers(communityId);
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @PutMapping("/users")
    public ResponseEntity<Void> saveUser(@RequestBody UserModel userModel) {
        userService.saveUser(userModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable String id) {
        UserModel user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable String id) {
        userService.removeUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
