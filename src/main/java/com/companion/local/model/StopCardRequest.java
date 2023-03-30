package com.companion.local.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StopCardRequest  extends CardRequest {

    private String reasonID;
    private String note;


    public StopCardRequest(String reference, String cardIdentifier, String newPIN, String transactionId, String transactionDate, String reasonID, String note) {
        super(reference, cardIdentifier, newPIN, transactionId, transactionDate);
        this.reasonID = reasonID;
        this.note = note;
    }
}
