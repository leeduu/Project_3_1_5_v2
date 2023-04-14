package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers() {   // Показ всех юзеров
        return "admin";
    }

    @GetMapping("/edit/{editId}") //форма апдейта юзера
    public String updateUser() {
        return "update";
    }

    @PatchMapping("/edit/{editId}") // апдейт юзера и показ всех юзеров
    public String update() {

        return "redirect:/admin";
    }

    @GetMapping("/new") // форма создания нового юзера
    public String newUserForm() {

        return "new";
    }

    @PostMapping("/new")    // сохранение нового юзера и показ всех юзеров
    public String newUser() {

        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{deleteId}")    //удаление юзера
    public String deleteUser() {
        return "redirect:/admin";
    }

}
