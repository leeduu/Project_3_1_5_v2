package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
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
    public List<Role> getList() {
        return roleRepository.getRolesList();
    }

    @Override
    public List<Role> findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
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
