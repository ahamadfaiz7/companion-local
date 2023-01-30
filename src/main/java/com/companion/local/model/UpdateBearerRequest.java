package com.companion.local.model;

import java.io.Serializable;

public class UpdateBearerRequest extends CreateLinkedCardRequest implements Serializable {

    private String cardIdentifier;


    public String getCardIdentifier() {
        return cardIdentifier;
    }

    public void setCardIdentifier(String cardIdentifier) {
        this.cardIdentifier = cardIdentifier;
    }
}
