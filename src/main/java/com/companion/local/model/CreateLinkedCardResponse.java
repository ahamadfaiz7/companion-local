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
public class CreateLinkedCardResponse implements Serializable {

    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String trackingNumber;
}
