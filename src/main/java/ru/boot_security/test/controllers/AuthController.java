package ru.boot_security.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.boot_security.test.entities.Role;
import ru.boot_security.test.entities.Roles;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.services.RoleService;
import ru.boot_security.test.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/auth/")
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @GetMapping("create")
    public String create(ModelMap model) {
        List<Role> roles = roleService.findAll();

//region Просто для теста, чтобы при удалении базы, ее не инициализировать вручную!
        int rolesTotalCount = Roles.values().length;

        if (roles.size() < rolesTotalCount) {
            for (int i = 0; i < rolesTotalCount; i++) {
                final Roles role = Roles.values()[i];

                if (roles.stream().noneMatch(r -> r.getRole().equals(role.name()))) {
                    roleService.save(new Role(role));
                }
            }

            roles = roleService.findAll();
        }
//endregion

        model.addAttribute("new", new User());
        model.addAttribute("title", "Create");
        model.addAttribute("roles", roles);

        return "create";
    }

    @PostMapping("create")
    public String create(@ModelAttribute("new") User user) {
        userService.save(user);
        return "redirect:/auth/login";
    }

    @GetMapping("login")
    public String login(ModelMap model) {
        model.addAttribute("new", new User());
        model.addAttribute("title", "Login");
        return "login";
    }
}
