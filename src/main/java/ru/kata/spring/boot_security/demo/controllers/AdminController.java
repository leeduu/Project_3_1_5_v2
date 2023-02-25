package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
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
        model.addAttribute("roles", roleRepository.getRolesList());
        return "update";
    }

    @PatchMapping("/{id}/update") // апдейт юзера и показ всех юзеров
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Integer id) {
//        if (bindingResult.hasErrors()) {
//            return "update";
//        }
        userService.update(id, user);
        return "redirect:/admin";
    }

    // --------------------- NEW  -----------------------------

    @GetMapping("/new") // форма создания нового юзера
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.getRolesList());
        return "new";
    }

    @PostMapping("/new")    // сохранение нового юзера и показ всех юзеров
    public String newUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "new";
//        }
        userService.save(user);
        return "redirect:/admin";
    }
//    @PostMapping("/admin/new")
//    public ResponseEntity<HttpStatus> newUser(@RequestBody @Valid User user) {
//        userService.save(user);
//        return ResponseEntity.ok(HttpStatus.OK);    // отправляется HTTP ответ с пустым телом и статусом 200
//    }


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
