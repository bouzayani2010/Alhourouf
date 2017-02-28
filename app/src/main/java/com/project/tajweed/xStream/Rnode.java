package com.project.tajweed.xStream;

import java.util.List;

/**
 * Created by Belgacem on 25/02/2017.
 */
public class Rnode {
    private static Rnode ourInstance = new Rnode();
    private List<Nodea> nodes;


    private String name;

    public static Rnode getInstance() {
        return ourInstance;
    }

    private Rnode() {
    }

    public List<Nodea> getNodes() {
        return nodes;
    }

    public void setNodes(List<Nodea> nodes) {
        this.nodes = nodes;
    }

    public void add(Nodea nodea) {
        nodes.add(nodea);
    }
    public boolean hasNode(Nodea nodea) {
    /*    for(Nodea nodea_item:nodes){
            if(nodea_item.getName().equals(nodea.getName())){
                return true;
            }
        }*/
        return false;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
