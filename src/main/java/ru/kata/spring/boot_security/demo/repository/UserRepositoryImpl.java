package ru.kata.spring.boot_security.demo.repository;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private EntityManager entityManager;
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsername(String username) {
        return entityManager.createQuery("select distinct u from User u left join fetch u.roles where u.username = :username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    public List<User> showAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }   //"SELECT username, email FROM User WHERE id IN (SELECT user_id FROM users_roles)", User.class

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }


}
