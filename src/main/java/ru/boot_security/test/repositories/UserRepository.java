package ru.boot_security.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.boot_security.test.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User AS u JOIN FETCH u.roles WHERE u.username=?1")
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    @Query("SELECT u FROM User AS u WHERE u.id NOT IN (?1)")
    List<User> findAllExcept(long id);
}
