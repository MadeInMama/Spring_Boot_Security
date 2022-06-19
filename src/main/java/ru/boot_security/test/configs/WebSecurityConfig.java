package ru.boot_security.test.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.boot_security.test.entities.Roles;
import ru.boot_security.test.services.UserService;

@Configuration
@EnableWebSecurity
@SuppressWarnings({"PMD.SignatureDeclareThrowsException"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Bean
    public static PasswordEncoderWithDecoder passwordEncoderWithDecoder() {
        return new PasswordEncoderWithDecoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/auth/**").not().authenticated()
                .antMatchers("/home/**").authenticated()
                .antMatchers("/admin/**").hasAuthority(Roles.ADMIN.name())
                .antMatchers("/users/**").hasAnyAuthority(Roles.ADMIN.name())
                .antMatchers("/user/**").hasAnyAuthority(Roles.ADMIN.name(), Roles.USER.name())
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/auth/login")
                .successHandler(getLoginSuccessHandler())

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Bean
    public AuthenticationSuccessHandler getLoginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoderWithDecoder());
    }
}