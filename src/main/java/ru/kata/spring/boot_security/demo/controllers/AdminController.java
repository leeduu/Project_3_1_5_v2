package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("showAllUsers", userService.showAllUsers());
        return "admin";
    }

    @GetMapping("/admin/update/{username}") // форма апдейта юзера
    public String updateUser(Model model, @PathVariable("username") String username) {
        model.addAttribute("updateUser", userService.findByUsername(username));
        return "update";
    }

    @PatchMapping(value = "/admin/update/{username}")   //апдейт юзера и показ всех юзеров
    public String update(@ModelAttribute("updatedUser") User user, @PathVariable("username") String username) {
        userService.update(user);
        return "redirect:/admin";
    }
}
