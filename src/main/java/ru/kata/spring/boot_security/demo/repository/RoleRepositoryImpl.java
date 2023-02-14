package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository{

    private EntityManager entityManager;
    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Role> findByIdRoles(List<Integer> id) {
        return entityManager.createQuery("select from Role where id in :id", Role.class).setParameter("id", id).getResultList();
    }
}
