//package ru.kata.spring.boot_security.demo.services;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.kata.spring.boot_security.demo.models.User;
//import ru.kata.spring.boot_security.demo.repository.UserRepository;
//
//@Service
//public class NewUserService {
//
//    private final UserRepository userRepository;
//    public NewUserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    public void register(User user) {
//        userRepository.save(user);
//    }
//}
