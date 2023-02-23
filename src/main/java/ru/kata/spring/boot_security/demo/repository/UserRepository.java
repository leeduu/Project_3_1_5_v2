package ru.kata.spring.boot_security.demo.repository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository /*extends JpaRepository<User, Integer> */{
    User findByUsername(String username);
    List<User> showAllUsers();
    void update(int id, User user);
    void save(User user, List<Integer> selectedRoles) throws Exception;
    void delete(String username);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
