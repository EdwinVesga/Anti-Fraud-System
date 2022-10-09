package antifraud.constant;

import antifraud.exception.InvalidRoleException;

import java.util.Locale;

public enum UserRoleType {
    ROLE_ADMINISTRATOR("ADMINISTRATOR"), ROLE_SUPPORT("SUPPORT"), ROLE_MERCHANT("MERCHANT");

    private String role;

    UserRoleType(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return role;
    }

    public static UserRoleType getUserRoleType(String role) {
        try {
            role = role.toUpperCase(Locale.ROOT);

            if (role.contains("ROLE")) {
                return UserRoleType.valueOf(role);
            }

            return UserRoleType.valueOf("ROLE_" + role);
        } catch (IllegalArgumentException e) {
            throw new InvalidRoleException();
        }

    }
}
