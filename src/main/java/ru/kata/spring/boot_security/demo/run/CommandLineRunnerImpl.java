package ru.kata.spring.boot_security.demo.run;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

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

        Set<Role> userRoles = new HashSet<>();
        Role userRole = new Role(1L, "ROLE_USER");
        roleService.saveRole(userRole);
        userRoles.add(userRole);

        Set<Role> adminRoles = new HashSet<>();
        Role adminRole = new Role(2L, "ROLE_ADMIN");
        roleService.saveRole(adminRole);
        adminRoles.add(adminRole);
        adminRoles.add(userRole);

        User testUser = new User("test1", "test1", "test1@mail.ru", userRoles);
        User testAdmin = new User("tester", "tester", "tester@bk.ru", adminRoles);
//        userService.update(testUser);
//        userService.update(testAdmin);
    }
}
