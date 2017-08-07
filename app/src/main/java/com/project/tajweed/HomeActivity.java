package com.project.tajweed;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.tajweed.adapters.ListtajweedAdapter;
import com.project.tajweed.xStream.Nodea;
import com.project.tajweed.xStream.Rnode;

import org.lucasr.twowayview.TwoWayView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by bbouzaiene on 31/07/2017.
 */

public class HomeActivity extends Activity implements AdapterView.OnItemClickListener {

    private static Rnode rNode;
    private ListtajweedAdapter adapter;
    private static List<Section> cartList;
    @InjectView(R.id.listview)
    ListView listView;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;

    @InjectView(R.id.tv_desc)
    TextView tv_desc;
    @InjectView(R.id.tv_back)
    TextView tv_back;

    private List<Nodea> nodes = new ArrayList<>();
    //private Stack stack;
    private List stack_path;
    private List<String> items_path;
    private PathAdapter pathAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.inject(this);
        //stack = new Stack();
        stack_path = new Stack();
        //listView = (ListView) findViewById(R.id.listview);

        parseXML();
        parseNodeDOMXML(this);
        // stack.push(nodes);
        stack_path.add(0, rNode);

        nodes = rNode.getNodes();
        refreshlistview();
        listView.setOnItemClickListener(this);
        items_path = new ArrayList<String>();
        createviews();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void refreshlistview() {
   /*     List<Nodea> al = new ArrayList<>();
// add elements to al, including duplicates
        Set<Nodea> hs = new HashSet<>();
        hs.addAll(nodes);
        al.clear();
        al.addAll(hs);
        Collections.reverse(al);*/
        adapter = new ListtajweedAdapter(this, nodes);
        listView.setAdapter(adapter);
    }

    private void createviews() {
        //  items_path = new ArrayList(stack_path);
        items_path = stack_path;
        //   Collections.reverse(items_path);
        // Collections.sort(items_path,Collections.<String>reverseOrder());
        //  items_path.add(getString(R.string.tajweed));
        pathAdapter = new PathAdapter(this, items_path);
        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(pathAdapter);
        lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
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
          /*  ListView listView = (ListView) findViewById(R.id.listview);
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
            Log.d("nodeanodea", ":" + e.getMessage());
        }


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
                // rNode.setSection(getSectionfrompath(eElement.getAttribute("name")));
                NodeList nList = root.getChildNodes();
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element enode = (Element) node;
                        Nodea nodea = new Nodea();
                        nodea.setName(enode.getAttribute("name"));

                        nodea.setSection(getSectionfrompath(eElement.getAttribute("name")
                                + "/"
                                +
                                enode.getAttribute("name")
                        ));

                        //Log.d("Rootelement :", "" + node.getNodeName() + " " + enode.getLocalName() + " " + enode.getAttribute("name"));
                        NodeList nList1 = node.getChildNodes();
                        for (int j = 0; j < nList1.getLength(); j++) {
                            Node node1 = nList1.item(j);
                            if (node1.getNodeType() == Node.ELEMENT_NODE) {
                                Element enode1 = (Element) node1;
                                Nodea nodea1 = new Nodea();
                                nodea1.setName(enode1.getAttribute("name"));
                                nodea1.setSection(getSectionfrompath(eElement.getAttribute("name")
                                        + "/" + enode.getAttribute("name")
                                        + "/" + enode1.getAttribute("name")
                                ));
                                //////////
                                NodeList nList2 = node1.getChildNodes();
                                for (int k = 0; k < nList2.getLength(); k++) {
                                    Node node2 = nList2.item(k);
                                    if (node2.getNodeType() == Node.ELEMENT_NODE) {
                                        Element enode2 = (Element) node2;
                                        Nodea nodea2 = new Nodea();
                                        nodea2.setName(enode2.getAttribute("name"));
                                        nodea2.setSection(getSectionfrompath(eElement.getAttribute("name")
                                                + "/" + enode.getAttribute("name")
                                                + "/" + enode1.getAttribute("name")
                                                + "/" + enode2.getAttribute("name")
                                        ));
                                        //////////
                                        NodeList nList3 = node2.getChildNodes();
                                        if (nList3.getLength() > 0) {
                                            for (int l = 0; l < nList2.getLength(); l++) {
                                                Node node3 = nList3.item(l);
                                                if (node3 != null && node3.getNodeType() == Node.ELEMENT_NODE) {
                                                    Element enode3 = (Element) node3;
                                                    Nodea nodea3 = new Nodea();
                                                    nodea3.setName(enode3.getAttribute("name"));
                                                    nodea3.setSection(getSectionfrompath(eElement.getAttribute("name")
                                                            + "/" + enode.getAttribute("name")
                                                            + "/" + enode1.getAttribute("name")
                                                            + "/" + enode2.getAttribute("name")
                                                            + "/" + enode3.getAttribute("name")
                                                    ));

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

    private static Section getSectionfrompath(String path) {
        //Log.d("cartlist", "" + path);
        // Log.d("cartlist",""+cartList.toString());
        for (Section section : cartList) {

            String sectionpath = section.getPath().replace(" ", "");
            path = path.replace(" ", "");

            //Log.d("cartlist", "**" + sectionpath + "**" + path);
            if (path.equals(sectionpath)) {
                Log.d("cartlist", "**" + sectionpath + "**" + path);
                return section;
            }
        }
        return null;

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        try {
            Nodea nodea = nodes.get(i);
            List<Nodea> nodeas = nodea.getNodes();

            if (nodeas.size() > 0) {
                nodes = nodea.getNodes();
                // nodes.clear();
                //    nodes = nodeas;

                // stack.push(nodes);

                stack_path.add(0, nodea);
                createviews();
                refreshlistview();
                // adapter.notifyDataSetChanged();
            }
            if (!nodea.getSection().getDesc().isEmpty()) {
                // adapter = new ListtajweedAdapter(this, nodes);
                //listView.setAdapter(adapter);
                for (int k = 0; k < nodes.size(); k++) {
                    try {
                        View kView = listView.getChildAt(k);
                        kView.setBackgroundResource(R.drawable.background_transparent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                scrollView.setVisibility(View.GONE);
                tv_desc.setText(nodea.getSection().getDesc());
                scrollView.setVisibility(View.VISIBLE);
                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                //  tv_name.setTextColor(R.color.red_color);
                view.setBackgroundResource(R.drawable.gray_shape);

            } else {

                scrollView.setVisibility(View.GONE);
            }
            Log.d("nodeanodea", "::" + nodea.getName());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("nodeanodea", e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        //
        try {
            if (stack_path.size() > 1) {
                stack_path.remove(0);
                Object nd = stack_path.get(0);
                if (nd instanceof Nodea) {
                    nodes = ((Nodea) nd).getNodes();
                    Log.d("nodessize","::"+nodes.size());
                } else if (nd instanceof Rnode) {
                    nodes = ((Rnode) nd).getNodes();
                    Log.d("nodessize","::"+nodes.size());
                }
            } else {
                super.onBackPressed();
            }
            Log.d("nodessize","::"+nodes.size());
            if (nodes.size() > 0) {
                createviews();
                refreshlistview();
                scrollView.setVisibility(View.GONE);
            } else {

                super.onBackPressed();
            }
            // nodes = (List<Nodea>) stack.pop();
        } catch (EmptyStackException e) {
            e.printStackTrace();
            super.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
            super.onBackPressed();
        }
    }
}
