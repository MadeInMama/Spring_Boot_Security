package ru.boot_security.test.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditUserDto {
    public Long id;
    public String username;
    public String password;
    public Set<Role> roles;
    public List<Role> rolesTotal;

    public EditUserDto(long id, String username, String password, Set<Role> roles, List<Role> totalRoles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.rolesTotal = totalRoles;
    }

    public EditUserDto(List<Role> totalRoles) {
        this.id = null;
        this.username = "";
        this.password = "";
        this.roles = new HashSet<>();
        this.rolesTotal = totalRoles;
    }
}
