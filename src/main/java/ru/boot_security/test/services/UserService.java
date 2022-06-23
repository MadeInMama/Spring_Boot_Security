package ru.boot_security.test.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.boot_security.test.entities.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findById(long id);

    void save(User user);

    void deleteById(long id);

    @Override
    @Query("SELECT u FROM User AS u JOIN FETCH u.roles WHERE u.username=?1")
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
