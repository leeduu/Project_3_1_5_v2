package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;
//    public RoleRepositoryImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    public List<Role> findRoleByName(String name) {
        return entityManager.createQuery("from Role where name in :name", Role.class).setParameter("name", name).getResultList();
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
