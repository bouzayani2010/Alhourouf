package com.project.tajweed;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity {

    private static final String ns = null;
    private DetailActivity detailActivity;
    public ArrayList<Node> nodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout l1 = (LinearLayout) this.findViewById(R.id.linearLayout1);

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
        l1.addView(tView.getView());

        detailActivity = DetailActivity.newInstance();
        parseXML();
        parseNodeXML();
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


            final ArrayList<Section> cartList = myXMLHandler.getCartList();
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
        }


    }

    private void parseNodeXML() {
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
            for (Node node : nodeList) {
                Log.d("nodenode", ":" + node.getName());
            }

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
            Log.d("nodenode",e.getMessage());
        }


    }
}
