package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.security.auth.Subject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    // --------------------- UPDATE  -----------------------------

    @GetMapping("/{id}/update") //форма апдейта юзера
    public String updateUser(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("rolesList", roleService.getRolesList());
        return "update";
    }

    @PatchMapping("/{id}/update") // апдейт юзера и показ всех юзеров
    public String update(@RequestParam(name = "roles", defaultValue = "0") String[] chosenRoles,
                         @ModelAttribute("user") User user,
                         BindingResult bindingResult,
                         @PathVariable("id") Integer id, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("rolesList", roleService.getRolesList());
//            return "update";
//        }
        List<Role> newRoles = new ArrayList<>();
        for (String role : chosenRoles) {
            Integer roleId = Integer.valueOf(role);
            newRoles.add(roleService.findRole(roleId));
        }
        user.setRoles(newRoles);
        userService.update(id, user);
        return "redirect:/admin";
    }

    // --------------------- NEW  -----------------------------

    @GetMapping("/new") // форма создания нового юзера
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRolesList());
        return "new";
    }

    @PostMapping("/new")    // сохранение нового юзера и показ всех юзеров
    public String newUser(@RequestParam(name = "roles", defaultValue = "0") String[] chosenRoles,
                          @ModelAttribute("user") User user,
                          BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("roles", roleService.getRolesList());
//            return "new";
//        }
        List<Role> newRoles = new ArrayList<>();
        for (String role : chosenRoles) {
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
