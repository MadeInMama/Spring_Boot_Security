package ru.boot_security.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.boot_security.test.configs.PasswordEncoderWithDecoder;
import ru.boot_security.test.entities.User;
import ru.boot_security.test.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home/")
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoderWithDecoder passwordEncoder;

    @GetMapping("")
    public String profile(Model model, Principal principal) {
        User user = userService.findById(((User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId());
        user.setPassword(passwordEncoder.decode(user.getPassword()));

        List<User> users = userService.findAllExcept(user.getId());
        users.forEach(r -> r.setPassword(passwordEncoder.decode(r.getPassword())));

        model.addAttribute("title", "Profile");
        model.addAttribute("user", user);

        return "profile";
    }
}
