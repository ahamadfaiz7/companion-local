package com.companion.local.request;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Value implements Serializable {
    private String string;
    private String dateTimeIso8601;

    public String getString() { return string; }
    public void setString(String value) { this.string = value; }

    public String getDateTimeIso8601() {
        return dateTimeIso8601;
    }

    public void setDateTimeIso8601(String dateTimeIso8601) {
        this.dateTimeIso8601 = dateTimeIso8601;
    }

    public Value() {
    }
}
