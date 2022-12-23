package com.companion.local.request;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class MethodCall implements Serializable {
    private String methodName;
    private Params params;

    public String getMethodName() { return methodName; }
    public void setMethodName(String value) { this.methodName = value; }

    public Params getParams() { return params; }
    public void setParams(Params value) { this.params = value; }

    public MethodCall() {

    }
}