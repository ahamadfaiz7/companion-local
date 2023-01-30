package com.companion.local.model.LinkedCards;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement
public class Struct {
    private List<Member> member = new ArrayList<Member>();
    public List<Member> getMember() {
        return member;
    }
    public void setMember(List<Member> member) {
        this.member = member;
    }
}
