package com.companion.local.model;

import java.io.Serializable;

public class UpdateBearerRequest extends CreateLinkedCardRequest implements Serializable {

    private String cardIdentifier;

    public UpdateBearerRequest(String reference, String firstName, String lastName, String idOrPassport, String cellphoneNumber, String expiryDate, String transactionId, String transactionDate) {
        super(reference, firstName, lastName, idOrPassport, cellphoneNumber, expiryDate, transactionId, transactionDate);
    }


    public String getCardIdentifier() {
        return cardIdentifier;
    }

    public void setCardIdentifier(String cardIdentifier) {
        this.cardIdentifier = cardIdentifier;
    }
}
