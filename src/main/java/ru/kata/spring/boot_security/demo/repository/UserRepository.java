package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository {
    User findUser(Long id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<User> showAllUsers();
    void update(User user);
    void save(User user);
    void delete(Long id);
}
