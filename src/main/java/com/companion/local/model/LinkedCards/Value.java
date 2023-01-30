package com.companion.local.model.LinkedCards;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name="value")
public class Value {
    private Struct struct;
    public Struct getStruct() {
        return struct;
    }
    public void setStruct(Struct struct) {
        this.struct = struct;
    }
}
