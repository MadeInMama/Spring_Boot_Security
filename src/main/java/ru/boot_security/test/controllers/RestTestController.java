package ru.boot_security.test.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.boot_security.test.configs.PasswordEncoderWithDecoder;
import ru.boot_security.test.entities.EditUserDto;
import ru.boot_security.test.entities.Role;
import ru.boot_security.test.entities.Roles;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.services.RoleService;
import ru.boot_security.test.services.UserService;

import java.security.Principal;
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
        User user = userService.findById(id);
        user.setPassword(passwordEncoder.decode(user.getPassword()));

        return user;
    }

    @GetMapping("edit/user/{id}")
    public Object findUserForEdit(@PathVariable long id) {
        EditUserDto eud;

        if (id > 0) {
            User user = userService.findById(id);

            eud = new EditUserDto(user.getId(), user.getUsername(),
                    passwordEncoder.decode(user.getPassword()),
                    user.getRoles(), roleService.findAll());
        } else {
            eud = new EditUserDto(roleService.findAll());
        }

        return eud;
    }

    @GetMapping("get/user/me")
    public User findMe(Principal principal) {
        User user = userService.findById(((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId());
        user.setPassword(passwordEncoder.decode(user.getPassword()));

        return user;
    }

    @GetMapping("get/users")
    public List<User> findUsers(Principal principal) {
        List<User> users;
        User user;

        if (principal != null) {
            user = userService.findById(((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId());
            users = userService.findAllExcept(user.getId());
        } else {
            users = userService.findAll();
        }

        users.forEach(r -> r.setPassword(passwordEncoder.decode(r.getPassword())));

        return users;
    }

    @PostMapping("delete/user")
    public void deleteUser(@RequestParam("id") long id) {
        userService.deleteById(id);
    }

    @PostMapping("save/user")
    public void addUser(@RequestParam("id") long id,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("roles[]") String[] roles) {
        Set<Role> rolesSet = new HashSet<>();

        for (String role : roles) {
            Roles r = Roles.createFromString(role);

            if (r != null) {
                Role item = roleService.findByRole(r.name());

                if (item == null) {
                    item = new Role(r);
                }

                rolesSet.add(item);
            }
        }

        userService.save(new User(id, username, password, rolesSet));
    }
}
