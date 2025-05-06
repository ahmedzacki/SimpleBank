package com.ahmed.simpleBank.business;

public enum Role {
    ADMIN("Admin"),
    USER("User"),
    GUEST("Guest");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static Role fromString(String roleName) {
        for (Role role : Role.values()) {
            if (role.getRoleName().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid Role: " + roleName);
    }
}
