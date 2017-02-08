package com.project.tajweed;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class OrderXMLHandler extends DefaultHandler {

    boolean currentElement = false;
    String currentValue = "";

    String cartId;
    String customerId;
    String email;
    Node node;
    ArrayList<Node> nodeList;

    public String getCartId() {
        return cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = true;

        if (qName.equals("rNode")) {
            nodeList = new ArrayList<Node>();
        } else if (qName.equals("node")) {
            node = new Node();
            node.setName(attributes.getValue(0));
        }

    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        currentElement = false;
        if (qName.equalsIgnoreCase("node"))
          //  node.setParent(nodeList.get(nodeList.))
            nodeList.add(node);

        currentValue = "";
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);
        }

    }

}