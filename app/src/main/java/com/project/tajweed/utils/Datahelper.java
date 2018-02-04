package com.project.tajweed.utils;


import com.project.tajweed.Section;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Belgacem on 23/02/2017.
 */
public class Datahelper {
    private Node nodeXmlStructure;
    private ArrayList<Section> nodeXmlData;

    public static Datahelper getInstance() {
        if (instance == null)
            instance = new Datahelper();
        return instance;
    }

    private static Datahelper instance;

    public void setNodeXmlStructure(Node nodeXmlStructure) {
        this.nodeXmlStructure = nodeXmlStructure;

    }

    public Node getNodeXmlStructure() {
        return nodeXmlStructure;
    }

    public void setNodeXmlData(ArrayList<Section> nodeXmlData) {
        this.nodeXmlData = nodeXmlData;
    }

    public ArrayList<Section> getNodeXmlData() {
        return nodeXmlData;
    }
}
