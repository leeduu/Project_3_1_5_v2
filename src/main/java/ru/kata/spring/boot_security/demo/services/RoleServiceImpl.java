package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
//
//    @Override
//    public List<Role> findByIdRoles(int id) {
//        return null;
//    }
//
    @Override
    public List<Role> getRolesList() {
        return roleRepository.getRolesList();
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public Role findRole(Integer id) {
        return roleRepository.findRole(id);
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleRepository.addRole(role);
    }


//    @Transactional
//    @Override
//    public List<Role> findByIdRoles(int id) {
//        return roleRepository.findRoleById(id);
//    }
//
//    @Override
//    public List<Role> getRolesList() {
//        return roleRepository.getRolesList();
//    }
}
