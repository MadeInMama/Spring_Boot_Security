package ru.boot_security.test.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.boot_security.test.entities.Roles;
import ru.boot_security.test.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("")
    public String homeAdmin(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && ((User) auth.getPrincipal()).getRoles().stream().anyMatch(r -> Objects.equals(r.getRole(), Roles.ADMIN.name()))) {
            return "redirect:/admin/";
        }

        return "redirect:/user/";
    }
}
