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

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> showAllUsers() {
        return userRepository.showAllUsers();
    }

    @Transactional
    @Override
    public void update(int id, User user) {
        userRepository.update(id, user);
    }

    @Transactional
    @Override
    public void save(User user) throws Exception {
        // добавить проверку юзернейма?
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(String username) {
        userRepository.delete(username);
    }

}
