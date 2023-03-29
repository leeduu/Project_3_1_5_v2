package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MyRestController {

    private final UserService userService;

    public MyRestController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/users")   // показ всех юзеров
//    public List<User> showAllUsers() {
//        List<User> usersList = new ArrayList<>();
//        userService.showAllUsers().forEach(usersList::add);
//        return usersList;
//    }

    @GetMapping("/users")   // показ всех юзеров
    public ResponseEntity<List<User>> showAllUsers() {
        return new ResponseEntity<>(userService.showAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")   // поиск юзера по id
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
    }

    @PostMapping("/users")   // новый юзер
    public ResponseEntity<User> newUser(@RequestBody User newUser) {
        userService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/users")   // апдейт юзера
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")   // удаление юзера
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/user")   // данные пользователя
    public ResponseEntity<User> getUser(@AuthenticationPrincipal User user) {
//        User user = userService.findUserByEmail(principal.getName());
//        model.addAttribute("showMyInfo", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
