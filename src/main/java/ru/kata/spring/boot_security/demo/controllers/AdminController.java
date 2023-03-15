package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

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
    public String showAllUsers(Principal principal, Model model) {   // Показ всех юзеров                          DONE
        model.addAttribute("showAllUsers", userService.showAllUsers());
        model.addAttribute("rolesList", roleService.getRolesList());
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("auth_user", user);
        return "admin";
    }

    @GetMapping("/edit/{editId}") //форма апдейта юзера
    public String updateUser(Model model, @RequestParam(name = "editId") Long id) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("rolesList", roleService.getRolesList());
        return "update";
    }

    @PatchMapping("/edit/{editId}") // апдейт юзера и показ всех юзеров   || ModelAttribute не нужен в update
    public String update(/*@ModelAttribute("user") User user,*/
                         @RequestParam(name = "rolesList", defaultValue = "1") String[] roles,
                         @PathVariable("editId") Long id) {
        Set<Role> newRoles = new HashSet<>();
        for (String role : roles) {
            Long roleId = Long.valueOf(role);
            newRoles.add(roleService.findRole(roleId));
        }
        User user = userService.findUser(id);
        user.setRoles(newRoles);
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/new") // форма создания нового юзера                                                      DONE
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("rolesList", roleService.getRolesList());
        return "new";
    }

    @PostMapping("/new")    // сохранение нового юзера и показ всех юзеров                                  DONE
    public String newUser(@ModelAttribute("user") User user,
                          @RequestParam(name = "role", defaultValue = "1") String role) {
        Set<Role> newRoles = new HashSet<>();
            Long roleId = Long.valueOf(role);
            newRoles.add(roleService.findRole(roleId));
        user.setRoles(newRoles);
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{deleteId}")    //удаление юзера || В delete лучше RequestParam
    public String deleteUser(@PathVariable("deleteId") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
