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
public class UnstopCardActionRequest extends CardActionRequest implements Serializable {

    private String note;

    public UnstopCardActionRequest(String reference, String cardIdentifier, String transactionId, String transactionDate, String note) {
        super(reference, cardIdentifier, transactionId, transactionDate);
        this.note = note;
    }
}
