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
public class TransferLinkCardRequest implements Serializable {

    private String reference;
    private String oldCardIdentifier;
    private String newCardIdentifier;
    private String transactionId;
    private String transactionDate;
}
