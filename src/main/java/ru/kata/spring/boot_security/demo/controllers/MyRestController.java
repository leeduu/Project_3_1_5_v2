package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MyRestController {

    private final UserService userService;
    private final RoleService roleService;

    public MyRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

    @PostMapping("/users/new")   // новый юзер
    public ResponseEntity<User> newUser(@RequestBody User newUser) {
        userService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PatchMapping("/users/{id}")   // апдейт юзера
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")   // удаление юзера
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/user")   // данные пользователя
    public User getUser(@CurrentSecurityContext(expression = "authentication")
                                        Authentication authentication) {
        return userService.findUserByEmail(authentication.getName());
    }

    @GetMapping("/roles")   // все роли
    public ResponseEntity<List<Role>> showAllRoles() {
        return new ResponseEntity<>(roleService.getRolesList(), HttpStatus.OK);
    }
}
