package ru.kata.spring.boot_security.demo.run;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    public CommandLineRunnerImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleService.getRolesList().isEmpty()) {
            Set<Role> userRoles = new HashSet<>();
            Role userRole = new Role("ROLE_USER");
            roleService.saveRole(userRole);
            userRoles.add(userRole);

            Set<Role> adminRoles = new HashSet<>();
            Role adminRole = new Role("ROLE_ADMIN");
            roleService.saveRole(adminRole);
            adminRoles.add(adminRole);
            adminRoles.add(userRole);

            User defaultUser = new User("user", "$2a$12$RPP8411JN6Y0oDclViEjQumnZdliFNk2vnfqvs2OimtEVFZ4xRkr.", "user@mail.ru", userRoles);
            userService.save(defaultUser);
            User defaultAdmin = new User("admin", "$2a$12$fgaakdfIcz7SIetm9RA4ee6mq/80Oeu.rNa3nev3qzFYj/w6KG75W", "admin@mail.ru", adminRoles);
            userService.save(defaultAdmin);
            System.out.println(defaultUser);
            System.out.println(defaultAdmin);
        }
    }
}
