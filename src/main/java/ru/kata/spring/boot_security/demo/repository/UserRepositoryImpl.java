package ru.kata.spring.boot_security.demo.repository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private EntityManager entityManager;
    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
    public UserRepositoryImpl(EntityManager entityManager,
                              RoleRepository roleRepository) {
        this.entityManager = entityManager;
        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
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
    public void update(int id, User user) {
        entityManager.merge(user);
    }

    // ДОПОЛНИТЬ
    @Override
    public void save(User user) throws Exception {
            if (userExists(user.getUsername())) {
                throw new Exception("User with these details already exists");
            }
            // реализовать передачу выбранной роли
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
//         newUser.setRoles(Arrays.asList((Role) roleRepository.findRoleByName("ROLE_USER")));
//        if (newUser.getRoles().equals("USER")) {
//            Role roleUser = (Role) roleRepository.findRoleByName("ROLE_USER");
//            newUser.setRoles((List<Role>) roleUser);
//        }
        entityManager.persist(user);
    }

    private boolean userExists(String username) {
        return findByUsername(username) != null;
    }

    @Override
    public void delete(String username) {
        User user = findByUsername(username);
        entityManager.remove(entityManager.find(User.class, user.getId()));
    }

}
