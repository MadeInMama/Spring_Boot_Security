package ru.boot_security.test.services;

import ru.boot_security.test.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    List<User> findAllExcept(long id);

    User findById(long id);

    void save(User user);

    void deleteById(long id);
}
