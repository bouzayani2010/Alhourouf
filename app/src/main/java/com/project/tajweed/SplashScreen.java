package com.project.tajweed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.project.tajweed.Custom.TypeWriter;
import com.project.tajweed.utils.Datahelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SplashScreen extends Activity {

    private long SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, HomeActivity1.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        ArrayList<Section> dataList = parseXML();
        Datahelper.getInstance().setNodeXmlData(dataList);
        Node ndRoot = parseNodeDOMXML1(this);
        Datahelper.getInstance().setNodeXmlStructure(ndRoot);

        TypeWriter typewriter = findViewById(R.id.textView);
        typewriter.setCharacterDelay(100);
        typewriter.setTextSize(30);
        typewriter.setTypeface(null, Typeface.BOLD);
        typewriter.setPadding(20, 20, 20, 20);
        // typewriter.setAnimationCompleteListener(animationCompleteCallBack);
        if (ndRoot.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) ndRoot;
            String nameRoot = eElement.getAttribute("name");
            typewriter.animateText(nameRoot);
        }
        //  typewriter.animateText(getString(R.string.tajweed));
    }


    private Node parseNodeDOMXML1(Context context) {

        // rNode = Rnode.getInstance();
        AssetManager assetManager = context.getAssets();

        try {
            InputStream is = assetManager.open("tajweed_structure.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();
            Log.d("Rootelement :", "" + doc.getDocumentElement().getNodeName());
            Node root = doc.getFirstChild();
            if (root.getNodeType() == Node.ELEMENT_NODE) {
                return root;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Section> parseXML() {
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


            ArrayList<Section> dataList = myXMLHandler.getCartList();

            //
            is.close();
            return dataList;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("nodeanodea", ":" + e.getMessage());
        }
        return null;


    }
}
