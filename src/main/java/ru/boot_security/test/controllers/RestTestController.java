package ru.boot_security.test.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.boot_security.test.configs.PasswordEncoderWithDecoder;
import ru.boot_security.test.entities.Role;
import ru.boot_security.test.entities.Roles;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.services.RoleService;
import ru.boot_security.test.services.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        User user = userService.findById(id);
        user.setPassword(passwordEncoder.decode(user.getPassword()));

        return user;
    }

    @GetMapping("get/users")
    public List<User> findUsers(Principal principal) {
        List<User> users = userService.findAll();
        User user;

        if (principal != null) {
            user = userService.findById(((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId());
            users = users.stream().filter(u -> !u.getId().equals(user.getId())).collect(Collectors.toList());
        }

        users.forEach(r -> r.setPassword(passwordEncoder.decode(r.getPassword())));

        return users;
    }

    @PostMapping("delete/user/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }

    @PostMapping("add/user")
    public void addUser(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("roles[]") int[] roles) {
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

        userService.save(new User(username, password, rolesSet));
    }
}
