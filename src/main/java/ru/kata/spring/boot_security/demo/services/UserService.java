package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);
    List<User> showAllUsers();
    void update(int id, User user);
    void save(User user) throws Exception;
    void delete(String username);
}
