package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public List<User> showAllUsers() {
        return userRepository.showAllUsers();
    }

    @Override
    public User update(User user) {
        return userRepository.update(user);
    }

//    @Transactional
//    public User showUser(int username) {
//        return userRepository.findByUsername(username);
//    }


}
