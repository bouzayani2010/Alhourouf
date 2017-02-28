package com.project.tajweed.xStream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belgacem on 23/02/2017.
 */

@XStreamAlias("node")
public class Nodeb {

    public Nodeb(String name) {
        this.name=name;
    }

    public void setDetail(List<Nodeb> nodes) {
        this.nodes = nodes;
    }

    @XStreamImplicit(itemFieldName="node")
    private List<Nodeb> nodes= new ArrayList();;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XStreamAlias("name")
    private String name;

    public void add(Nodeb nodea) {
        nodes.add(nodea);
    }

    public boolean hasNode(Nodeb nodea) {
        for(Nodeb nodea_item:nodes){
            if(nodea_item.getName().equals(nodea.getName())){
                return true;
            }
        }
        return false;
    }

    public List<Nodeb> getNodes() {
        return nodes;
    }
}
