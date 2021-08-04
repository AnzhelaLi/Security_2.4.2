package org.example.controller;

import org.example.dao.UserDao;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @GetMapping("/{id}")
    public String userPage(/*@PathVariable String id,*/ Principal principal, Model model) {
        User username = userDao.getUserByName(principal.getName());
        model.addAttribute("user", username);
        return "users/userInfo";
    }

    @GetMapping
    public String showUser(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "users/userInfo";
    }

}
