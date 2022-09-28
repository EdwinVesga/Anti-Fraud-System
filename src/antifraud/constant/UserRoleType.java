package antifraud.constant;

public enum UserRoleType {
    ROLE_ADMINISTRATOR("ADMINISTRATOR"), ROLE_SUPPORT("SUPPORT"), ROLE_MERCHANT("MERCHANT");

    private String role;

    UserRoleType(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return role;
    }
}
