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
}
