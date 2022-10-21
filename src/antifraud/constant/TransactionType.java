package antifraud.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TransactionType {
    ALLOWED, MANUAL_PROCESSING, PROHIBITED;

    @JsonValue
    @Override
    public String toString() {
        return this.name();
    }

    public static boolean exists(String transactionType) {
        return Arrays.stream(TransactionType.values()).anyMatch(t -> t.name().equals(transactionType));
    }
}
