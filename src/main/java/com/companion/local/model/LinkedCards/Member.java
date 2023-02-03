package com.companion.local.model.LinkedCards;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Member {
    private String name;
    private Value_ value;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Value_ getValue() {
        return value;
    }
    public void setValue(Value_ value) {
        this.value = value;
    }
}
