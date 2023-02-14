package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository /*extends JpaRepository<User, Integer> */{
    User findByUsername(String username);
    List<User> showAllUsers();
}
