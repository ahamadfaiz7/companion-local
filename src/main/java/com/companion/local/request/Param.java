package com.companion.local.request;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Param implements Serializable {
    private Value value;

    public Value getValue() { return value; }
    public void setValue(Value value) { this.value = value; }

    public Param() {
    }
}
