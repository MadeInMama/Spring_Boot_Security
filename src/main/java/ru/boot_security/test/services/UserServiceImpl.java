package ru.boot_security.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.boot_security.test.entities.Role;
import ru.boot_security.test.entities.Roles;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.repositories.RoleRepository;
import ru.boot_security.test.repositories.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        Role role;

        if (user.getUsername().contains("admin")) {
            role = roleRepository.findByRole(Roles.ADMIN.name());

            if (role == null) {
                role = new Role(Roles.ADMIN);
                roleRepository.save(role);
            }
        } else {
            role = roleRepository.findByRole(Roles.USER.name());

            if (role == null) {
                role = new Role(Roles.USER);
                roleRepository.save(role);
            }
        }

        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }
}
