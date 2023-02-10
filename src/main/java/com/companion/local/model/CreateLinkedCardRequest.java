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
public class CreateLinkedCardRequest implements Serializable {

    private String reference;
    private String firstName;
    private String lastName;
    private String idOrPassport;
    private String cellphoneNumber;
    private String expiryDate;
    private String transactionId;
    private String transactionDate;
}
