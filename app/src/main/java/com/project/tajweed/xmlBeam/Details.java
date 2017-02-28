package com.project.tajweed.xmlBeam;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belgacem on 23/02/2017.
 */

@XStreamAlias("Details")
public class Details {
    public Detaila detaila;

    public Detailb getDetailb() {
        return detailb;
    }

    public void setDetailb(Detailb detailb) {
        this.detailb = detailb;
    }

    public Detailb detailb;

    public Detaila getDetaila() {
        return detaila;
    }

    public void setDetaila(Detaila detaila) {
        this.detaila = detaila;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    @XStreamImplicit(itemFieldName="detail")
    private List<Detail> detail= new ArrayList();;

}
