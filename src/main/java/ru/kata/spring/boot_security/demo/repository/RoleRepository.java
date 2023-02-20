package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleRepository /*extends JpaRepository<User, Integer> */{

//    List<Role> findRoleById(int id);
    List<Role> getRolesList();
    List<Role> findRoleByName(String name);
}
