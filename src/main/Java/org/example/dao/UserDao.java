package org.example.dao;

import org.example.model.User;

import java.util.List;


public interface UserDao {

    User justSaveUser(User user);

    void deleteUser(Long id);

    User updateUser(User updatedUser);

    List<User> usersList();

    User findUserById(Long id);

    User getUserByName(String username);
}
