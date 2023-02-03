package com.companion.local.model;

public class StopCardRequest  extends CardRequest {

    private String reasonID;
    private String note;

    public String getReasonID() {
        return reasonID;
    }

    public void setReasonID(String reasonID) {
        this.reasonID = reasonID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
