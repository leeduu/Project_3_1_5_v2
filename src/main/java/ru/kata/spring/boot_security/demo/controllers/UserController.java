package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")   //
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String showUserProfile(Model model, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("showMyInfo", user);
        return "user";
    }

}
