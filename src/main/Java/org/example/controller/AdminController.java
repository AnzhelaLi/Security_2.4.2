package org.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.example.model.Role;
import org.example.service.RoleService;
import org.example.service.UserService;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/allUsers")//полный список
    public String list(Model model) {

        model.addAttribute("users", userService.usersList());
        return "users/list";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {

        model.addAttribute("user", userService.findUserById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}") //@Valid, после аннотации - BindingResult!
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        String[] rolesForUpdate = request.getParameterValues("roles");// принимает данные с чекбокса (все - отмеченные и нет)
        Set<Role> rolesSetForUpdate = roleService.rolesFromCheckbox(rolesForUpdate);
        userService.updateUser(user, rolesSetForUpdate);
        return "redirect:/admin/allUsers";
    }

    @GetMapping("/new") //форма для нового юзера
    public String newUser(Model model) {

        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping//перенаправление на страницу всех юзеров
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        String[] rolesForUpdate = request.getParameterValues("roles");
        Set<Role> rolesSetForRegister = roleService.rolesFromCheckbox(rolesForUpdate);
        userService.registerUser(user, rolesSetForRegister);
        return "redirect:/admin/allUsers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/allUsers";
    }
}
