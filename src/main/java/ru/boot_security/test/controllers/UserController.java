package ru.boot_security.test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("title", "User");
        return "index";
    }
}
