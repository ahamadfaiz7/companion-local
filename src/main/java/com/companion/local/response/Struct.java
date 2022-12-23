package com.companion.local.response;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Struct implements Serializable {
    private List<Member> member = new ArrayList<Member>();

    public List<Member> getMember() {
        return member;
    }

    public void setMember(List<Member> member) {
        this.member = member;
    }

    public Struct() {

    }
}
