package com.project.tajweed.xStream;

import com.project.tajweed.xmlBeam.Detail;
import com.project.tajweed.xmlBeam.Detaila;
import com.project.tajweed.xmlBeam.Detailb;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belgacem on 23/02/2017.
 */

@XStreamAlias("rNode")
public class Rnode2 {

    public List<Nodea> getNodes() {
        return nodes;
    }

    public void setNodes(List<Nodea> nodes) {
        this.nodes = nodes;
    }

    @XStreamImplicit(itemFieldName="node")
    private List<Nodea> nodes= new ArrayList();
}
