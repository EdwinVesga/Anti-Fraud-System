package antifraud.dto;

import antifraud.constant.TransactionResult;

public class TransactionResponseDTO {

    private TransactionResult result;

    public TransactionResult getResult() {
        return result;
    }

    public void setResult(TransactionResult result) {
        this.result = result;
    }
}
