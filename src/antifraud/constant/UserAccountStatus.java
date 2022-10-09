package antifraud.constant;

import antifraud.exception.InvalidUserOperation;

import java.util.Locale;

public enum UserAccountStatus {
    LOCK, UNLOCK;

    public static UserAccountStatus getStatus(String status) {
        try {
            return UserAccountStatus.valueOf(status.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new InvalidUserOperation();
        }
    }
}
