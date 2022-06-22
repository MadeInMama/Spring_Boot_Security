package ru.boot_security.test.entities;

public enum Roles {
    ADMIN,
    USER;

    public static Roles createFromInt(int i) {
        for (Roles role : Roles.values()) {
            if (role.ordinal() == i) {
                return role;
            }
        }

        return null;
    }
}
