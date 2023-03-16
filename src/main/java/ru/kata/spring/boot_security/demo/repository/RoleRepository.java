package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleRepository {
    List<Role> getRolesList();
    Role findRoleByName(String name);
    Role findRole(Long id);
    void saveRole(Role role);
    void deleteRole(Role role);
}
