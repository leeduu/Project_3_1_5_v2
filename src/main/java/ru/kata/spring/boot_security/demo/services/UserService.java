package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findUser(Long id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User getAuthUser();
    List<User> showAllUsers();
    void update(User user);
    void save(User user);
    void delete(Long id);
}
