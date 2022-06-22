package ru.boot_security.test.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import ru.boot_security.test.configs.PasswordEncoderWithDecoder;
import ru.boot_security.test.entities.Role;
import ru.boot_security.test.entities.Roles;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.services.RoleService;
import ru.boot_security.test.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/")
public class RestTestController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoderWithDecoder passwordEncoder;

    @GetMapping("get/user/{id}")
    public User findUser(@PathVariable long id) {
        return userService.findById(id);
    }

    @GetMapping("get/users")
    public List<User> findUsers() {
        return userService.findAll();
    }

    @GetMapping("delete/user/{id}")
    public List<User> deleteUser(@PathVariable long id) {
        userService.deleteById(id);
        return findUsers();
    }


    @PostMapping("add/user")
    public void addUser(@Param("username") String username,
                        @Param("password") String password,
                        @Param("roles") int[] roles) {
        Set<Role> rolesSet = new HashSet<>();

        for (int role : roles) {
            Roles r = Roles.createFromInt(role);

            if (r != null) {
                Role item = roleService.findByRole(r.name());

                if (item == null) {
                    item = new Role(r);
                }

                rolesSet.add(item);
            }
        }

        User user = new User(username, password, rolesSet);
    }
}
