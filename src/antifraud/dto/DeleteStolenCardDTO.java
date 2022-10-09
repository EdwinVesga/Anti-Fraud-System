package antifraud.dto;

import lombok.Getter;

@Getter
public class DeleteStolenCardDTO {

    private static final String DELETE_STOLEN_CARD_MSG = "Card %s successfully removed!";

    private String status;

    public DeleteStolenCardDTO(String cardNumber) {
        this.status = String.format(DELETE_STOLEN_CARD_MSG, cardNumber);
    }
}
