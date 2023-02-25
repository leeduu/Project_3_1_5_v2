package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

//    @PersistenceContext
    private final EntityManager entityManager;
    public RoleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findRoleByName(String name) {
        return entityManager.find(Role.class, name);
//                .createQuery("from Role where name in :name", Role.class).setParameter("name", name).getResultList();
    }

    @Override
    public Role findRole(Integer id) {
        return entityManager.find(Role.class, id);
//                .createQuery("from Role where name in :name", Role.class).setParameter("name", name).getResultList();
    }

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    // ПЕРЕПИСАТЬ
//    @Override
//    public List<Role> findRoleById(int id) {
//        return (List<Role>) entityManager.createQuery("from Role where id in :id", Role.class).setParameter("id", id);
//    }
//
    @Override
    public List<Role> getRolesList() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }



}
