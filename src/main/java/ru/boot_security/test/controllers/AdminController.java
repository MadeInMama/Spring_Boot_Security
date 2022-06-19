package ru.boot_security.test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("title", "Admin");
        return "index";
    }
}
