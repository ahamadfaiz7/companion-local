package com.companion.local.model;

import java.io.Serializable;

public class ToggleVoucherFeatureRequest implements Serializable {

    private String reference;
    private String cardIdentifier;
    private String featureName;
    private String featureStatus;
    private String transactionId;
    private String transactionDate;

    public ToggleVoucherFeatureRequest(String reference, String cardIdentifier, String featureName, String featureStatus, String transactionId, String transactionDate) {
        this.reference = reference;
        this.cardIdentifier = cardIdentifier;
        this.featureName = featureName;
        this.featureStatus = featureStatus;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
    }

    public ToggleVoucherFeatureRequest() {
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCardIdentifier() {
        return cardIdentifier;
    }

    public void setCardIdentifier(String cardIdentifier) {
        this.cardIdentifier = cardIdentifier;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureStatus() {
        return featureStatus;
    }

    public void setFeatureStatus(String featureStatus) {
        this.featureStatus = featureStatus;
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
