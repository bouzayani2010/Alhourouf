package com.project.tajweed;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.tajweed.xStream.*;
import com.project.tajweed.xmlBeam.Detail;
import com.project.tajweed.xmlBeam.Detaila;
import com.project.tajweed.xmlBeam.Detailb;
import com.project.tajweed.xmlBeam.Details;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Belgacem on 23/02/2017.
 */
public class Utils {
    public static void parseNodeXML(Context context) {

        XStream xStream = new XStream(new DomDriver());
        AssetManager assetManager = context.getAssets();


        InputStream inputStream = null;
        try {
            // xStream.registerConverter((Converter) new MapEntryConverter());
            xStream.alias("rNode", Rnode2.class);
            xStream.alias("node", String.class);
            xStream.registerConverter(new ToAttributedValueConverter(Nodea.class, xStream.getMapper(),
                    xStream.getReflectionProvider(), xStream
                    .getConverterLookup(), "message"));
            xStream.addImplicitCollection(Rnode2.class, "node", Nodea.class);
            //xStream.addImplicitCollection(Detail.class,"message", String.class);
            xStream.useAttributeFor(Nodea.class, "name");

            inputStream = assetManager.open("tajweed_structure1.xml");
            String xml = readFully(inputStream);
            Toast.makeText(context, xml, Toast.LENGTH_LONG).show();
            Rnode2 details = (Rnode2) xStream.fromXML(xml);
            Log.d("detail", " : " + details.toString());
            List<Nodea> listofdetails = details.getNodes();
            for (Nodea det : listofdetails) {

                Log.d("detail a", " : " + det.getMessage().toString());
            }
            Log.d("detail a", " : " + details.getNodes().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void parseNodeXMLdetail(Context context) {

        XStream xStream = new XStream(new DomDriver());
        AssetManager assetManager = context.getAssets();


        InputStream inputStream = null;
        try {
            // xStream.registerConverter((Converter) new MapEntryConverter());
            xStream.alias("Details", Details.class);
            xStream.alias("detail", String.class);
            xStream.registerConverter(new ToAttributedValueConverter(Detail.class, xStream.getMapper(),
                    xStream.getReflectionProvider(), xStream
                    .getConverterLookup(), "message"));
            xStream.addImplicitCollection(Details.class, "detail", Detail.class);
            //xStream.addImplicitCollection(Detail.class,"message", String.class);
            xStream.useAttributeFor(Detail.class, "code");

            inputStream = assetManager.open("detail1.xml");
            String xml = readFully(inputStream);
            Toast.makeText(context, xml, Toast.LENGTH_LONG).show();
            Details details = (Details) xStream.fromXML(xml);
            Log.d("detail", " : " + details.toString());
            List<Detail> listofdetails = details.getDetail();
            for (Detail det : listofdetails) {

                Log.d("detail a", " : " + det.getMessage().toString());
            }
            Log.d("detail a", " : " + details.getDetail().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String readFully(InputStream entityResponse) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = entityResponse.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString();
    }

    public static void parseNodeDOMXML(Context context) {
        AssetManager assetManager = context.getAssets();

        try {
            InputStream is = assetManager.open("tajweed_structure.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();
            Log.d("Rootelement :", "" + doc.getDocumentElement().getNodeName());

            //NodeList nList = doc.getElementsByTagName("node");
            Node root = doc.getFirstChild();
            if (root.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) root;
                NodeList nList = root.getChildNodes();
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element enode = (Element) node;
                        Log.d("Rootelement :", "" + node.getNodeName() + " " + enode.getLocalName() + " " + enode.getAttribute("name"));
                        NodeList nList1 = node.getChildNodes();
                        for (int j = 0; j < nList1.getLength(); j++) {
                            Node node1 = nList1.item(j);
                            if (node1.getNodeType() == Node.ELEMENT_NODE) {
                                Element enode1 = (Element) node1;
                                Log.d("Rootelement :", "" + node1.getNodeName() + " " + enode1.getLocalName() + " " + enode1.getAttribute("name"));
                            }
                        }

                    }
                }
            }


        } catch (IOException e) {
            Log.d("Rootelement :", "" + e.getMessage());
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            Log.d("Rootelement PConfEx:", "" + e.getMessage());
            e.printStackTrace();
        } catch (SAXException e) {
            Log.d("Rootelement SAXException:", "" + e.getMessage());
            e.printStackTrace();
        }

    }

/*    public static void add(List<Nodea> list, int i) {
        Rnode rnode = Rnode.getInstance();
        Nodea last_node = list.get(1);
        Nodea nd1 = list.get(i);
        if (i > 2) {
            for (int j = 2; j < i; j++) {
                Nodea nd = list.get(j);
                if (last_node.hasNode(nd)) {
                    last_node = nd;
                }
            }
        }

    }*/
/*
    private void parseNodeXML() {


        XStream xStream = new XStream(new DomDriver());
        AssetManager assetManager = getBaseContext().getAssets();

        try {

            // xStream.registerConverter((Converter) new MapEntryConverter());
            xStream.alias("Details", Details.class);
            xStream.alias("detaila", Detaila.class);
            xStream.alias("detailb", Detailb.class);
            xStream.alias("detail", String.class);
            xStream.registerConverter(new ToAttributedValueConverter(Detail.class, xStream.getMapper(),
                    xStream.getReflectionProvider(), xStream
                    .getConverterLookup(), "message"));
            xStream.addImplicitCollection(Detaila.class, "detail", Detail.class);
            xStream.addImplicitCollection(Detailb.class, "detail", Detail.class);
            //xStream.addImplicitCollection(Detail.class,"message", String.class);
            xStream.useAttributeFor(Detail.class, "code");

            InputStream inputStream = assetManager.open("detail.xml");
            String xml = readFully(inputStream);
            Toast.makeText(getApplicationContext(), xml, Toast.LENGTH_LONG).show();
            Details details = (Details) xStream.fromXML(xml);
            Log.d("detail", " : " + details.toString());
            Log.d("detail a", " : " + details.detaila.getDetail().toString());
            Log.d("detail b", " : " + details.detailb.getDetail().toString());

            List<Detail> details_of_a = details.detaila.getDetail();
            for (Detail det : details_of_a) {
                Log.d("detail a message", " : " + det.getMessage());
                Log.d("detail a code", " : " + det.getCode());
            }
            List<Detail> details_of_b = details.detailb.getDetail();
            for (Detail det : details_of_b) {
                Log.d("detail b message", " : " + det.getMessage());
                Log.d("detail b code", " : " + det.getCode());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/
}
