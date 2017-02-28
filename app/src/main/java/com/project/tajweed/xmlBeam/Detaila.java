package com.project.tajweed.xmlBeam;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belgacem on 23/02/2017.
 */

@XStreamAlias("detaila")
public class Detaila {
    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    @XStreamImplicit(itemFieldName="detail")
    private List<Detail> detail= new ArrayList();;
}
