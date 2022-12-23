package com.companion.local.response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement
@XmlType(name="value1")
public class Value implements Serializable {
    private Struct struct;

    public Struct getStruct() {
        return struct;
    }
    public void setStruct(Struct struct) {
        this.struct = struct;
    }

    public Value() {

    }

}
