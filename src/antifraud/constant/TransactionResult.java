package antifraud.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionResult {
    ALLOWED, MANUAL_PROCESSING, PROHIBITED;

    @JsonValue
    public String getResult() {
        return this.name();
    }
}
