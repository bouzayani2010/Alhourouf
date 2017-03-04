package com.project.tajweed.xStream;

import com.project.tajweed.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belgacem on 25/02/2017.
 */
public class Rnode {
    private static Rnode ourInstance = new Rnode();
    private List<Nodea> nodes=new ArrayList<Nodea>();


    private String name;
    private Section section;

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
   
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
