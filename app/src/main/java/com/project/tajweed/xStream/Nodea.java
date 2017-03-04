package com.project.tajweed.xStream;


import com.project.tajweed.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belgacem on 23/02/2017.
 */

public class Nodea {
    String name;
    String message;
    private List<Nodea> nodes=new ArrayList<Nodea>();
    private Section section;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Nodea nodea1) {
        nodes.add(nodea1);
    }

    public List<Nodea> getNodes() {
        return nodes;
    }

    public void setNodes(List<Nodea> nodes) {
        this.nodes = nodes;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Section getSection() {
        return section;
    }
}
