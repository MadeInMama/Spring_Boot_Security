package ru.boot_security.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public String home(Model model, Principal principal) {
        model.addAttribute("title", "User");
        model.addAttribute("user", userService.findById(((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId()));

        return "index";
    }
}
