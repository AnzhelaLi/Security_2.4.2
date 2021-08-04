package org.example.service;

import org.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;

public interface UserService {

    User registerUser(User user);

    User justSaveUser(User user);

    void deleteUser(Long id);

    User updateUser(User user);

    List<User> usersList();

    User findUserById(Long id);

    UserDetails loadUserByUsername(String username);

    void addInitData();
}
