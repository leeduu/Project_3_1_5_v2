package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRepository /*extends JpaRepository<User, Integer> */{
    User findUser(Integer id);
    User findUserByUsername(String username);
    List<User> showAllUsers();
    void update(Integer id, User user);
    void save(User user);
    void delete(Integer id);
//    void addRole(Role role);
//    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
