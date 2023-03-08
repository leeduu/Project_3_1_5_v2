package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final EntityManager entityManager;
    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleByName(String name) {
        return (Role) entityManager.createQuery("from Role where name in :name", Role.class).setParameter("name", name).getResultList();
    }

    @Override
    public Role findRole(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public void editRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public void deleteRole(Role role) {
        entityManager.remove(role);
    }

    @Override
    public List<Role> getRolesList() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }
}
