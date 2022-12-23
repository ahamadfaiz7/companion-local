package com.companion.local.response;

import com.sun.xml.txw2.annotation.XmlNamespace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;

@XmlRootElement
@XmlType(name="value")
public class Value_ implements Serializable {

    private String _boolean;
    private String string;
    private Integer _int;
    private String dateTime_iso8601;


    public String get_boolean() {
        return _boolean;
    }

    public void set_boolean(String _boolean) {
        this._boolean = _boolean;
    }

    public Integer get_int() {
        return _int;
    }

    public void set_int(Integer _int) {
        this._int = _int;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getDateTime_iso8601() {
        return dateTime_iso8601;
    }

    public void setDateTime_iso8601(String dateTime_iso8601) {
        this.dateTime_iso8601 = dateTime_iso8601;
    }

    public Value_() {
    }
}
