package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService  {
    User findUser(Long id);
    User findUserByUsername(String username);
    List<User> showAllUsers();
    void update(Long id, User user);
    void save(User user);
    void delete(Long id);
}
