package ru.boot_security.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.boot_security.test.configs.PasswordEncoderWithDecoder;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/users/")
public class UsersController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoderWithDecoder passwordEncoder;

    @GetMapping("")
    public String getAll(Model model) {
        List<User> users = userService.findAll();
        users.forEach(u -> u.setPassword(passwordEncoder.decode(u.getPassword())));

        model.addAttribute("title", "Users");
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("save")
    public String save(@ModelAttribute(name = "user") User user) {
        userService.save(user);

        return "redirect:/users/";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable long id) {
        userService.deleteById(id);

        return "redirect:/users/";
    }
}
