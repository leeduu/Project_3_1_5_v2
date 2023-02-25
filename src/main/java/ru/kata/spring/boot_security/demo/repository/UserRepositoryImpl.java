package ru.kata.spring.boot_security.demo.repository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;
    public UserRepositoryImpl(EntityManager entityManager) {
          this.entityManager = entityManager;
    }

    @Override
    public User findUser(Integer id) {
//        return entityManager.createQuery("select distinct u from User u left join fetch u.roles where u.id = :id", User.class)
//                .setParameter("id", id).getSingleResult();
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) {
        return entityManager.createQuery("select distinct u from User u left join fetch u.roles where u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult();
    }

    @Override
    public List<User> showAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }   //"SELECT username, email FROM User WHERE id IN (SELECT user_id FROM users_roles)", User.class

    @Override
//    @Transactional
    public void update(Integer id, User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
//    @Transactional
    public void save(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
//    @Transactional
    public void delete(Integer id) {
        User user = findUser(id);
        entityManager.remove(findUser(id));
    }

//    @Override
//    @Transactional
//    public void addRole(Role role) {
//        entityManager.persist(role);
////                .createQuery("insert into users_roles (user_id, role_id) values (user.getId(), i)");
//    }
}
