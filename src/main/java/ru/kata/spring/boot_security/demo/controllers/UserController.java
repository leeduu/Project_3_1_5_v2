package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

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
}
