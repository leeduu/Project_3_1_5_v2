package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")   // приветственная страница
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String showUserProfile(Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("showUserProfile", user);
        return "user_profile";
    }

}
