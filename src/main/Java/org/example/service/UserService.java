package org.example.service;

import org.example.model.Role;
import org.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;
import java.util.Set;

public interface UserService {

    User registerUser(User user, Set<Role> rolesFromCheckbox);

    User justSaveUser(User user);

    void deleteUser(Long id);

    User updateUser(User user, Set<Role> roles);

    List<User> usersList();

    User findUserById(Long id);

    void addInitData();
}
