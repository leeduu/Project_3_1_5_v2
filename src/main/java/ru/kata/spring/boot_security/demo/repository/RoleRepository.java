package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface RoleRepository /*extends JpaRepository<User, Integer> */{
    List<Role> findByIdRoles(List<Integer> id);
}
