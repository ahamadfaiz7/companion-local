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
public class ToggleVoucherFeatureRequest implements Serializable {

    private String reference;
    private String cardIdentifier;
    private String featureName;
    private String featureStatus;
    private String transactionId;
    private String transactionDate;
}
