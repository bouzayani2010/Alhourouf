package com.project.tajweed;


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belgacem on 23/02/2017.
 */
public class Utils {



    public static String readFully(InputStream entityResponse) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = entityResponse.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString();
    }

    public static List<Node> filterNodes(NodeList nList) {
        List<Node> nl = new ArrayList<Node>();
        for(int i=0;i<nList.getLength();i++){
            Node ndRoot = nList.item(i);
            if (ndRoot.getNodeType() == Node.ELEMENT_NODE){
                nl.add(ndRoot);
            }
        }
        return nl;
    }

    public static String drawPath(List<Node> stack_path) {
        String path="";
        if(stack_path!=null && stack_path.size()>0){
            for(Node nd:stack_path){
                if (nd.getNodeType() == Node.ELEMENT_NODE) {
                    Element nodea = (Element) nd;
                    path=path+nodea.getAttribute("name");

                }
            }
        }
        return path;
    }
}
