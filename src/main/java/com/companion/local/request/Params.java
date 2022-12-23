package com.companion.local.request;

import com.companion.local.request.Param;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Params implements Serializable {
    private List<Param> param = new ArrayList<Param>();

    public List<Param> getParam() {
        return param;
    }

    public void setParam(List<Param> param) {
        this.param = param;
    }

    public Params() {
    }


}
