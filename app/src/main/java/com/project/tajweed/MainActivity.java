package com.project.tajweed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tajweed.xStream.Nodea;
import com.project.tajweed.xStream.Rnode;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity {

    private static final String ns = null;
    private static Rnode rNode;
    private DetailActivity detailActivity;
    public ArrayList<Node1> nodeList;
    private LinearLayout treeView;
    private ArrayList<Section> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        treeView = (LinearLayout) this.findViewById(R.id.linearLayout1);

        TreeNode root = TreeNode.root();


        ///parent
        MyHolder.IconTreeItem nodeItem = new MyHolder.IconTreeItem();
        nodeItem.text = "node";
        TreeNode parent = new TreeNode(nodeItem).setViewHolder(new MyHolder(this));
        ///child
        TreeNode child0 = new TreeNode(nodeItem).setViewHolder(new MyHolder(this));
        TreeNode child1 = new TreeNode(nodeItem).setViewHolder(new MyHolder(this));
        parent.addChildren(child0, child1);

        root.addChild(parent);
        AndroidTreeView tView = new AndroidTreeView(this, root);
        //   treeView.addView(tView.getView());

        detailActivity = DetailActivity.newInstance();
        // parseXML();
        //  parseNodeXML1();
        // Utils.parseNodeXML(this);
        parseNodeDOMXML(this);

        remplir_treeView();
        // parseNodeXML();
    }

    private void remplir_treeView() {
        TreeNode root = TreeNode.root();

        ///parent
        MyHolder.IconTreeItem nodeItem = new MyHolder.IconTreeItem();
        nodeItem.text = "" + rNode.getName();
        TreeNode parent = new TreeNode(nodeItem).setViewHolder(new MyHolder(this));
        /////////////
        List<Nodea> fils_rnodes = rNode.getNodes();
        for (Nodea na : fils_rnodes) {
            MyHolder.IconTreeItem naItem = new MyHolder.IconTreeItem();
            naItem.text = "" + na.getName();
            TreeNode child = new TreeNode(naItem).setViewHolder(new MyHolder(this));
            /////////////
            List<Nodea> fils_rnodes1 = na.getNodes();
            for (Nodea na1 : fils_rnodes1) {
                MyHolder.IconTreeItem naItem1 = new MyHolder.IconTreeItem();
                naItem1.text = "" + na1.getName();
                TreeNode child1 = new TreeNode(naItem1).setViewHolder(new MyHolder(this));
                /////////////
                List<Nodea> fils_rnodes2 = na1.getNodes();
                for (Nodea na2 : fils_rnodes2) {
                    MyHolder.IconTreeItem naItem2 = new MyHolder.IconTreeItem();
                    naItem2.text = "" + na2.getName();
                    TreeNode child2 = new TreeNode(naItem2).setViewHolder(new MyHolder(this));
                    /////////////
                    List<Nodea> fils_rnodes3 = na2.getNodes();
                    for (Nodea na3 : fils_rnodes3) {
                        MyHolder.IconTreeItem naItem3 = new MyHolder.IconTreeItem();
                        naItem3.text = "" + na3.getName();
                        TreeNode child3 = new TreeNode(naItem3).setViewHolder(new MyHolder(this));
                        child2.addChildren(child3);

                    }
                    /////////////
                    child1.addChildren(child2);

                }
                /////////////
                child.addChildren(child1);

            }
            /////////////
            parent.addChildren(child);

        }
        /////////////


        root.addChild(parent);
        AndroidTreeView tView = new AndroidTreeView(this, root);
        treeView.addView(tView.getView());

    }


    private void parseXML() {
        AssetManager assetManager = getBaseContext().getAssets();
        try {
            InputStream is = assetManager.open("tajweed_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            OrderXMLHandler1 myXMLHandler = new OrderXMLHandler1();
            xr.setContentHandler(myXMLHandler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);


            cartList = myXMLHandler.getCartList();
            Rnode rnode = Rnode.getInstance();
            List<Nodea> listnodes = new ArrayList<>();
            rnode.setNodes(listnodes);
            for (Section section : cartList) {
                String pathsection = section.getPath();
                Log.d("nodeanodea", ":" + pathsection);
                StringTokenizer st = new StringTokenizer(pathsection, "/");

                List<Nodea> list = new ArrayList();
                while (st.hasMoreTokens()) {
                    //System.out.println(st.nextToken());
                    String item_of_path = st.nextToken();
                    // Log.d("sectionsection",":"+item_of_path);
                    // Nodea nodea = new Nodea(item_of_path);
                    //  list.add(nodea);
                }
                for (int i = 0; i < list.size(); i++) {

                }
            }
            Log.d("rnodesize", ":" + rnode.getNodes().size());
           /* for(Nodea nodea:rnode.getNodes()){
                Log.d("rnodesize", ":" +nodea.getName());
                for(Nodea nodeas:nodea.getNodes()){
                    Log.d("rnodesize", ":" +nodeas.getName());
                }
            }*/


            ListView listView = (ListView) findViewById(R.id.listview);
            ListAdapter adapter = new ListAdapter(this, cartList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Section section = cartList.get(position);

                    DetailActivity.path = section.getPath();
                    DetailActivity.desc = section.getDesc();
                    DetailActivity.name = section.getName();
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    startActivity(intent);
                }
            });


            is.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("nodeanodea", ":" + e.getMessage());
        }


    }


    public String readFully(InputStream entityResponse) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = entityResponse.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString();
    }

    private void parseNodeXML1() {
        AssetManager assetManager = getBaseContext().getAssets();
        try {
            InputStream is = assetManager.open("tajweed_structure.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();


            OrderXMLHandler myXMLHandler = new OrderXMLHandler();
            xr.setContentHandler(myXMLHandler);
            InputSource inStream = new InputSource(is);
            xr.parse(inStream);


            nodeList = myXMLHandler.getNodeList();
            Map<String, Node> map_node = createMap(nodeList, cartList);

            TreeNode root = TreeNode.root();

            ///parent
            MyHolder.IconTreeItem nodeItem = new MyHolder.IconTreeItem();
            nodeItem.text = "node";
            TreeNode parent = new TreeNode(nodeItem).setViewHolder(new MyHolder(this));
            for (Node1 node : nodeList) {
                MyHolder.IconTreeItem new_nodeItem = new MyHolder.IconTreeItem();
                new_nodeItem.text = node.getName();
                Log.d("nodenode", ":" + node.getName());
                TreeNode child = new TreeNode(new_nodeItem).setViewHolder(new MyHolder(this));
                parent.addChildren(child);

            }

            root.addChild(parent);
            AndroidTreeView tView = new AndroidTreeView(this, root);
            treeView.addView(tView.getView());
           /* ListView listView = (ListView) findViewById(R.id.listview);
            ListAdapter adapter = new ListAdapter(this, cartList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Section section = cartList.get(position);

                    DetailActivity.path = section.getPath();
                    DetailActivity.desc = section.getDesc();
                    DetailActivity.name = section.getName();
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    startActivity(intent);
                }
            });
*/

            is.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("nodenode", e.getMessage());
        }


    }

    private Map<String, Node> createMap(ArrayList<Node1> nodeList, ArrayList<Section> cartList) {
        Map<String, Node> mapnode = new HashMap<String, Node>();
        for (Node1 node : nodeList) {
            Log.d("nodenodecartList", node.getName());
        }
        return null;
    }

    public static String readFromAssets(Context context, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = reader.readLine();
        while (mLine != null) {
            sb.append(mLine); // process line
            mLine = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }


    public static void parseNodeDOMXML(Context context) {
        rNode = Rnode.getInstance();
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
                rNode.setName(eElement.getAttribute("name"));
                NodeList nList = root.getChildNodes();
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element enode = (Element) node;
                        Nodea nodea = new Nodea();
                        nodea.setName(enode.getAttribute("name"));

                        //Log.d("Rootelement :", "" + node.getNodeName() + " " + enode.getLocalName() + " " + enode.getAttribute("name"));
                        NodeList nList1 = node.getChildNodes();
                        for (int j = 0; j < nList1.getLength(); j++) {
                            Node node1 = nList1.item(j);
                            if (node1.getNodeType() == Node.ELEMENT_NODE) {
                                Element enode1 = (Element) node1;
                                Nodea nodea1 = new Nodea();
                                nodea1.setName(enode1.getAttribute("name"));

                                //////////
                                NodeList nList2 = node1.getChildNodes();
                                for (int k = 0; k < nList2.getLength(); k++) {
                                    Node node2 = nList2.item(k);
                                    if (node2.getNodeType() == Node.ELEMENT_NODE) {
                                        Element enode2 = (Element) node2;
                                        Nodea nodea2 = new Nodea();
                                        nodea2.setName(enode2.getAttribute("name"));

                                        //////////
                                        NodeList nList3 = node2.getChildNodes();
                                        if (nList3.getLength() > 0) {
                                            for (int l = 0; l < nList2.getLength(); l++) {
                                                Node node3 = nList3.item(l);
                                                if (node3 != null && node3.getNodeType() == Node.ELEMENT_NODE) {
                                                    Element enode3 = (Element) node3;
                                                    Nodea nodea3 = new Nodea();
                                                    nodea3.setName(enode3.getAttribute("name"));


                                                    nodea2.add(nodea3);
                                                    //Log.d("Rootelement :", "" + node1.getNodeName() + " " + enode1.getLocalName() + " " + enode1.getAttribute("name"));
                                                }
                                            }
                                        }
                                        //////////
                                        nodea1.add(nodea2);
                                        //Log.d("Rootelement :", "" + node1.getNodeName() + " " + enode1.getLocalName() + " " + enode1.getAttribute("name"));
                                    }
                                }
                                //////////


                                nodea.add(nodea1);
                                Log.d("Rootelement :", "" + node1.getNodeName() + " " + enode1.getLocalName() + " " + enode1.getAttribute("name"));
                            }
                        }
                        rNode.add(nodea);
                    }
                }
            }
            int m = 0;
            Log.d("Rootelement :", "" + m);

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
}
