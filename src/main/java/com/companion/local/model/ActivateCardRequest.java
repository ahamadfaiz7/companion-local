package com.companion.local.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)

public class ActivateCardRequest implements Serializable {
    private String cardIdentifier;
    private String transactionId;
    private String transactionDate;

    public ActivateCardRequest(String cardIdentifier, String transactionId, String transactionDate) {
        this.cardIdentifier = cardIdentifier;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
    }
    public ActivateCardRequest() {

    }


    public String getCardIdentifier() {
        return cardIdentifier;
    }

    public void setCardIdentifier(String cardIdentifier) {
        this.cardIdentifier = cardIdentifier;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
