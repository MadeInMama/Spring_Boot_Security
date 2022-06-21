package ru.boot_security.test.services;

import ru.boot_security.test.entities.Role;

import java.util.List;

public interface RoleService {
    Role findByRole(String role);

    List<Role> findAll();

    void save(Role role);
}
