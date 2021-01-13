package com.project.stackoverflow.service;

import com.project.stackoverflow.model.UserModel;
import com.project.stackoverflow.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public List<UserModel> getUsers(String communityId) {
        return userRepository.getUsers(communityId);
    }

    @Transactional
    public void saveUser(UserModel userModel) {
        userRepository.saveUser(userModel);
    }

    public UserModel getUserById(String id) {
        return userRepository.getUserById(id);
    }

    @Transactional
    public void removeUser(String id) {
        userRepository.removeUser(id);
    }
}
