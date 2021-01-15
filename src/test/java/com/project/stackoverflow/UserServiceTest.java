package com.project.stackoverflow;


import com.project.stackoverflow.exception.UserException;
import com.project.stackoverflow.model.UserModel;
import com.project.stackoverflow.repository.UserRepository;
import com.project.stackoverflow.service.UserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;

    private static Optional<UserModel> user1;
    private static Optional<UserModel> user2;

    private static List<UserModel> users = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        user1 = Optional.of(new UserModel(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com", null, null));
        user2 = Optional.of(new UserModel(UUID.randomUUID().toString(), "Ion", "Popescu", "ionel@gmail.com", null, null));
        users.add(user1.get());
        users.add(user2.get());
    }

    @Test
    @DisplayName("Test invalid user")
    public void testInvalidUser() {
        doReturn(Optional.empty()).when(repository).getUserById(user1.get().getId());
        UserException exception = assertThrows(UserException.class, () ->
                service.getUserById(user1.get().getId()));
        assertEquals(UserException.UserErrors.USERS_NOT_FOUND, exception.getError());
    }

    @Test
    @DisplayName("No existing users")
    public void noExistingUsers() {
        doReturn(emptyList()).when(repository).getUsers(any());
        UserException exception = assertThrows(UserException.class, () ->
                service.getUsers(any()));
        assertEquals(UserException.UserErrors.USERS_NOT_FOUND, exception.getError());
    }

    @Test
    @DisplayName("Test save invalid user")
    public void invalidUserSave() {
        doReturn(false).when(repository).saveUser(user1.get());
        UserException exception = assertThrows(UserException.class, () ->
                service.saveUser(user1.get()));
        assertEquals(UserException.UserErrors.USER_COULD_NOT_BE_SAVED, exception.getError());
    }

    @Test
    @DisplayName("Update user")
    public void updateUser() {
        doReturn(true).when(repository).saveUser(user1.get());
        service.saveUser(user1.get());
    }

    @Test
    @DisplayName("Remove invalid user")
    public void invalidUserRemove() {
        doReturn(false).when(repository).removeUser(user1.get().getId());

        UserException exception = assertThrows(UserException.class, () -> service.removeUser(user1.get().getId()));
        assertEquals(UserException.UserErrors.USER_COULD_NOT_BE_REMOVED, exception.getError());
    }

    @Test
    @DisplayName("User removed")
    public void userRemoved() {
        doReturn(true).when(repository).removeUser(user1.get().getId());
        service.removeUser(user1.get().getId());
    }
}