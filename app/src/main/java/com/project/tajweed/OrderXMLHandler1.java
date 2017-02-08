package com.project.tajweed;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class OrderXMLHandler1 extends DefaultHandler {

    boolean currentElement = false;
    String currentValue = "";

    ArrayList<Section> cartList;
    private Section section;


    public ArrayList<Section> getCartList() {
        return cartList;
    }

    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = true;
        Log.d("xmlxml", qName + " " + attributes.getValue(0) + " " + attributes.getValue(1));
        if (qName.equals("sections")) {
            cartList = new ArrayList<Section>();
        } else if (qName.equals("section")) {
            section = new Section();
            section.setName(attributes.getValue(0));
            section.setPath(attributes.getValue(1));
        }

    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        try {
            currentElement = false;
            if (qName.equalsIgnoreCase("desc"))
                section.setDesc(currentValue.trim());

            else if (qName.equalsIgnoreCase("section"))
                cartList.add(section);

            currentValue = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = currentValue + new String(ch, start, length);
        }

    }


}