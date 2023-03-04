package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

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

    @GetMapping("")
    public String showAllUsers(Model model) {
        model.addAttribute("showAllUsers", userService.showAllUsers());
        return "admin";
    }

    @GetMapping("/{id}/update") //форма апдейта юзера
    public String updateUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("rolesList", roleService.getRolesList());
        return "update";
    }

    @PostMapping("/{id}/update") // апдейт юзера и показ всех юзеров
    public String update(@Valid @ModelAttribute("user") User user,
                         @RequestParam(name = "checkboxes", defaultValue = "1") String[] checkboxes,
                         @PathVariable("id") Integer id) {
        Set<Role> newRoles = new HashSet<>();
        for (String role : checkboxes) {
            Integer roleId = Integer.valueOf(role);
            newRoles.add(roleService.findRole(roleId));
        }
        user.setRoles(newRoles);
        userService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/new") // форма создания нового юзера
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRolesList());
        return "new";
    }

    @PostMapping("/new")    // сохранение нового юзера и показ всех юзеров
    public String newUser(@Valid @ModelAttribute("user") User user,
                        @RequestParam(name = "checkboxes", defaultValue = "1") String[] checkboxes) {
        Set<Role> newRoles = new HashSet<>();
        for (String role : checkboxes) {
            Integer roleId = Integer.valueOf(role);
            newRoles.add(roleService.findRole(roleId));
        }
        user.setRoles(newRoles);
System.out.println(user);
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")   //показывает детали одного юзера
    public String showUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("delete", userService.findUser(id));
        return "delete";
    }

    @DeleteMapping("/{id}/delete")    //удаление юзера
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
