package com.companion.local.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UpdateBearerRequest extends CreateLinkedCardRequest implements Serializable {

    private String cardIdentifier;

    public UpdateBearerRequest(String reference, String firstName, String lastName, String idOrPassport, String cellphoneNumber, String expiryDate, String transactionId, String transactionDate, String cardIdentifier) {
        super(reference, firstName, lastName, idOrPassport, cellphoneNumber, expiryDate, transactionId, transactionDate);
        this.cardIdentifier = cardIdentifier;
    }
}
