package com.companion.local.model;

import java.io.Serializable;

public class UnstopCardRequest extends RetireCardRequest implements Serializable {

    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
