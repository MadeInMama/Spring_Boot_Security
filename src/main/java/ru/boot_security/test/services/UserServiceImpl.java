package ru.boot_security.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.boot_security.test.configs.PasswordEncoderWithDecoder;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.repositories.RoleRepository;
import ru.boot_security.test.repositories.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoderWithDecoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllExcept(long id) {
        return userRepository.findAllExcept(id);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void save(User user) {
        User check = userRepository.findByUsername(user.getUsername());

        if (check == null || Objects.equals(check.getId(), user.getId())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}
