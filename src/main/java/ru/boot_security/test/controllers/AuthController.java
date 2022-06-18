package ru.boot_security.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.services.UserService;

@Controller
@RequestMapping("/auth/")
public class AuthController {
    @Autowired
    UserService userService;

    @GetMapping("create")
    public String create(ModelMap model) {
        model.addAttribute("new", new User());
        model.addAttribute("title", "Create");
        return "create";
    }

    @PostMapping(value = "create")
    public String create(@ModelAttribute("new") User user) {
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("login")
    public String login(ModelMap model) {
        model.addAttribute("new", new User());
        model.addAttribute("title", "Login");
        return "login";
    }
}
