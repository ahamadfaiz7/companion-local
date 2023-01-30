package com.companion.local.model.LinkedCards;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Root {
    private Data data;
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
}
