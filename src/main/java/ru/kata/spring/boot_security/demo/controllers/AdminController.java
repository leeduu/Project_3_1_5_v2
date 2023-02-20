package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("showAllUsers", userService.showAllUsers());
        return "admin";
    }

    @GetMapping("/admin/{username}/update") //форма апдейта юзера
    public String updateUser(Model model, @PathVariable("username") String username) {
        model.addAttribute("updateUser", userService.findUserByUsername(username));
        List<Role> roles = roleService.getList();
        model.addAttribute("rolesList", roles);
        return "update";
    }

    @PatchMapping("/admin/{username}/update") // апдейт юзера и показ всех юзеров
    public String update(@PathVariable("username") String username, @ModelAttribute("updatedUser") User user) {
        userService.update(user.getId(), user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/new") // форма создания нового юзера
    public String newUserForm(Model model) {
        model.addAttribute("newUserForm", new User());
        List<Role> roles = roleService.getList();
        model.addAttribute("rolesList", roles);
        return "new";
    }

    @PostMapping("/admin/new")    // сохранение нового юзера и показ всех юзеров
    public String newUser(@ModelAttribute("newUser") User user, BindingResult bindingResult) throws Exception {
        userValidator.validate(user, bindingResult);  //добавить валидацию в User
            if (bindingResult.hasErrors()) {
                return "new";
            }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{username}")   //показывает детали одного юзера
    public String showUser(Model model, @PathVariable("username") String username) {
        model.addAttribute("show_user", userService.findUserByUsername(username));
        return "show_user";
    }

    @DeleteMapping("/admin/{username}/delete")    //удаление юзера
    public String deleteUser(@PathVariable("username") String username) {
        userService.delete(username);
        return "redirect:/admin";
    }

}
