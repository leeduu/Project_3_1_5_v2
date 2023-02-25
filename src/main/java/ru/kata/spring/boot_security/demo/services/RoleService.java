package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface RoleService {
    List<Role> getRolesList();
    Role findRoleByName(String name);
    Role findRole(Integer id);
    void addRole(Role role);
    void editRole(Role role);
    void deleteRole(Role role);
}
