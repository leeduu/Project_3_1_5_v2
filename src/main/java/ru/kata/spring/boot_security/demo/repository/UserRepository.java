package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository /*extends JpaRepository<User, Integer> */{
    User findByUsername(String username);
    List<User> showAllUsers();
    void update(int id, User user);
    void save(User user) throws Exception;
    void delete(String username);
}
