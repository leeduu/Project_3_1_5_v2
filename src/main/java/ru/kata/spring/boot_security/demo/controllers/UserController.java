package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
public class UserController {

//    private final UserService userService;
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/")
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }

//    @GetMapping("/user")
//    public String showUserProfile(Model model, Principal principal) {
//        User user = userService.findUserByEmail(principal.getName());
//        model.addAttribute("showMyInfo", user);
//        return "user";
//    }

}
