package com.project.stackoverflow.service;

import com.project.stackoverflow.exception.UserException;
import com.project.stackoverflow.model.UserModel;
import com.project.stackoverflow.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getUsers(String communityId) {
        List<UserModel> users = userRepository.getUsers(communityId);
        if (users.isEmpty()) {
            throw UserException.usersNotFound();
        }
        return users;
    }

    @Transactional
    public void saveUser(UserModel userModel) {
        if (!userRepository.saveUser(userModel)) {
            throw UserException.userCouldNotBeSaved();
        }
    }

    public UserModel getUserById(String id) {
        Optional<UserModel> existingUser = userRepository.getUserById(id);
        if (existingUser.isEmpty()) {
            throw UserException.usersNotFound();
        }
        return existingUser.get();
    }

    @Transactional
    public void removeUser(String id) {
        if (!userRepository.removeUser(id)) {
            throw UserException.userCouldNotBeRemoved();
        }
    }
}
