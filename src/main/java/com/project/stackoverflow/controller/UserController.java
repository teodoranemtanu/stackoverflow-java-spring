package com.project.stackoverflow.controller;

import com.project.stackoverflow.model.UserModel;
import com.project.stackoverflow.service.UserService;
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
    @ResponseBody
    public List<UserModel> getUsers(@RequestParam(required = false) String communityId) {
        return userService.getUsers(communityId);
    }

    @PutMapping("/users")
    public void saveUser(@RequestBody UserModel userModel) {
        userService.saveUser(userModel);
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public UserModel getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/users/{id})")
    public void removeUser(@PathVariable String id) {
        userService.removeUser(id);
    }
}
