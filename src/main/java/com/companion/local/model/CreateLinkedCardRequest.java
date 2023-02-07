package com.companion.local.model;

import java.io.Serializable;

public class CreateLinkedCardRequest implements Serializable {

    private String reference;
    private String firstName;
    private String lastName;
    private String idOrPassport;
    private String cellphoneNumber;
    private String expiryDate;
    private String transactionId;
    private String transactionDate;

    public CreateLinkedCardRequest(String reference, String firstName, String lastName, String idOrPassport, String cellphoneNumber, String expiryDate, String transactionId, String transactionDate) {
        this.reference = reference;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idOrPassport = idOrPassport;
        this.cellphoneNumber = cellphoneNumber;
        this.expiryDate = expiryDate;
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
    }

    public CreateLinkedCardRequest() {
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdOrPassport() {
        return idOrPassport;
    }

    public void setIdOrPassport(String idOrPassport) {
        this.idOrPassport = idOrPassport;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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
