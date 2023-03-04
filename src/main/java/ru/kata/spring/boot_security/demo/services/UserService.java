package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findUser(Integer id);
    User findUserByUsername(String username);
    List<User> showAllUsers();
    void update(Integer id, User user);
    void save(User user);
    void delete(Integer id);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
