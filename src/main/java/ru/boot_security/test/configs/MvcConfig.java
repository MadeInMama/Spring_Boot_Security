package ru.boot_security.test.configs;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/create").setViewName("create");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("home");
    }
}
