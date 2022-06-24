package ru.boot_security.test.entities;

public enum Roles {
    ADMIN,
    USER;

    public static Roles createFromString(String name) {
        for (Roles role : Roles.values()) {
            if (role.name().equals(name)) {
                return role;
            }
        }

        return null;
    }
}
