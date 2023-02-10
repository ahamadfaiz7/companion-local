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
public class PrintLinkedCardRequest implements Serializable {

    private String title;
    private String initials;
    private String lastName;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String additionalData;
    private String contactNumber;
    private String cardIdentifier;
    private String transactionId;
    private String transactionDate;

}
