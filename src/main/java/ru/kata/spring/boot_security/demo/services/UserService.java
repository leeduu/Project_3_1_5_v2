package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User findUserByUsername(String username);
    List<User> showAllUsers();
    void update(int id, User user);
    void save(User user, List<Integer> selectedRoles) throws Exception;
    void delete(String username);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
