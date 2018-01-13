package com.project.tajweed;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.tajweed.adapters.ListNodeAdapter;
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
import java.util.EmptyStackException;
import java.util.List;
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

public class HomeActivity1 extends Activity implements AdapterView.OnItemClickListener {

    private static Node rNode;
    private static HomeActivity1 homeActivity;
    private ListtajweedAdapter adapter;
    private static List<Section> cartList;

    @InjectView(R.id.scrollView)
    ScrollView scrollView;

    @InjectView(R.id.tv_desc)
    TextView tv_desc;
    @InjectView(R.id.tv_back)
    TextView tv_back;

    // private List<Nodea> nodes = new ArrayList<>();
    //private Stack stack;
    private List<Node> stack_path;
    private List<Node> items_path;
    private PathAdapter pathAdapter;
    private WebView mWebView;
    private ImageView img;
    private static ListNodeAdapter listNodeAdapter;
    private static ListView listView;
    private NodeList nList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        mWebView = (WebView) this.findViewById(R.id.webview);
        listView = (ListView) this.findViewById(R.id.listview);
        img = (ImageView) this.findViewById(R.id.image_view);
        img.setVisibility(View.GONE);
        stack_path = new Stack();

        parseXML();
        //  parseNodeDOMXML(this);
        //   rNode = Rnode.getInstance();
        Node ndRoot = parseNodeDOMXML1(this);
        stack_path.add(0, ndRoot);

        //   nodes = rNode.getNodes();


        items_path = new ArrayList<>();
        refreshlistview(ndRoot);

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        listView.setOnItemClickListener(this);
    }

    public void refreshlistview(Node ndRoot) {

        Log.d("NodeName", ":::" + ndRoot.getNodeName());
        if (ndRoot.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) ndRoot;
            Log.d("NodeName", ":::" + eElement.getAttribute("name"));
            nList = ndRoot.getChildNodes();
            List<Node> nListFiltered = Utils.filterNodes(nList);
            Log.d("NodeName", ":::" + nList.getLength());
            if (nList != null && nList.getLength() > 0) {
                listNodeAdapter = new ListNodeAdapter(this, nListFiltered);
                listView.setAdapter(listNodeAdapter);
            }
        }
        createviews();
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


                Node nod = items_path.get(i);
                List<Node> nodes = Utils.filterNodes(nod.getChildNodes());
                if (nodes.size() > 0) {
                    createviews();
                    //  refreshlistview(ndRoot);
                    scrollView.setVisibility(View.GONE);
                }
                for (int k = 0; k < i; k++) {
                    stack_path.remove(0);
                    stack_path.remove(0);
                }
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
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("nodeanodea", ":" + e.getMessage());
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
            Node nd = listNodeAdapter.getItem(i);

            refreshlistview(nd);
            stack_path.add(0, nd);
            // listView.setVisibility(View.GONE);
        /*    List<Nodea> nodeas = nodea.getNodes();

            if (nodeas.size() > 0) {
                nodes = nodea.getNodes();

                stack_path.add(0, nodea);
              //  createviews();
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
                drawviews(nodea);
                scrollView.setVisibility(View.VISIBLE);
                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                //  tv_name.setTextColor(R.color.red_color);
                view.setBackgroundResource(R.drawable.gray_shape);

            } else {

                scrollView.setVisibility(View.GONE);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("nodeanodea", e.getMessage());
        }

    }

    private void drawviews(Node nodea) {
        img.setVisibility(View.VISIBLE);
       String path=Utils.drawPath(stack_path);
        Log.d("pathpath",path);
        Section section = getSectionfrompath(path);
        String desc = section.getDesc();
        String name = section.getName();

        img.setVisibility(View.VISIBLE);
        if (name.equals("الجوف: تجاويف الحلقوم")) {
            img.setImageResource(R.drawable.jawf_01);
        } else if (name.equals("الحلق")) {
            img.setImageResource(R.drawable.quaaf_05);
        } else if (name.equals("اللسان")) {
            img.setImageResource(R.drawable.wasat_lissan_07);
        } else if (name.equals("الشفتان")) {
            img.setImageResource(R.drawable.meem_baa_16);
        } else if (name.equals("الخيشوم")) {
            img.setImageResource(R.drawable.khayshoom_17);
        } else if (name.equals("1 - الجوف")) {
            img.setImageResource(R.drawable.jawf_01);
        } else if (name.equals("2 - أقصى الحلق")) {
            img.setImageResource(R.drawable.hamzaa_aa_02);
        } else if (name.equals("3 - وسط الحلق")) {
            img.setImageResource(R.drawable.ayn_7aa_03);
        } else if (name.equals("4 - آخر الحلق أي أدناه إلى الفم")) {
            img.setImageResource(R.drawable.ayn_7aa_03);
        } else if (name.equals("5 - أصل اللسان: أقصاه")) {
            img.setImageResource(R.drawable.kaaf_06);
        } else if (name.equals("6 - وسط اللسان")) {
            img.setImageResource(R.drawable.wasat_lissan_07);
        } else if (name.equals("7 - حافة اللسان من جهة الأضراس")) {
            img.setImageResource(R.drawable.al_dhaad_08);
        } else if (name.equals("8 - أدنى اللسان إلى طرفه")) {
            img.setImageResource(R.drawable.al_dhaad_08);
        } else if (name.equals("9 - طرف اللسان أي رأس اللسان أي من أسفل رأس اللسان")) {
            img.setImageResource(R.drawable.al_noon_09);
        } else if (name.equals("10 - من رأس اللسان و لكنه أدخل إلى جهة الظهر")) {
            img.setImageResource(R.drawable.al_raa_10);
        } else if (name.equals("11 - بين راس اللسان و الثنايا العليا")) {
            img.setImageResource(R.drawable.taa_daal_ta_11);
        } else if (name.equals("12 - من طرف اللسان و من فوق الثنايا السفلى")) {
            img.setImageResource(R.drawable.al_safeer_12);
        } else if (name.equals("13 - من طرف اللسان و لكن بينه و بين الثنايا العليا")) {
            img.setImageResource(R.drawable.taa_daal_ta_11);
        } else if (name.equals("14 - من بطن الشفة مع أطراف الثنايا المشرفة")) {
            img.setImageResource(R.drawable.al_faa_14);
        } else if (name.equals("الشفتان")) {
            img.setImageResource(R.drawable.al_waw_15);
        } else if (name.equals("15- من بين الشفتين لكنها داخلة إلى الداخل")) {
            img.setImageResource(R.drawable.al_waw_15);
        } else if (name.equals("16 - من بين الشفتين لكنها من الخارج")) {
            img.setImageResource(R.drawable.meem_baa_16);
        } else {
            img.setVisibility(View.GONE);
        }
        String html = "<html dir=\"rtl\" lang=\"\"><head><style>@font-face {font-family: framd;src: " +
                "url('file:///android_asset/fonts/framd.ttf');}.container_style {margin: 0; padding" +
                ": 10px 5px 10px 5px;color: #00aaa9a7;font-size: 16px;font-weight:normal;font-family: " +
                "'framd';}.detail_style {color: #00aaa9a7;font-size: 16px;font-weight:normal;}</style>" +
                "</head><body class=\"container_style\"><div class=\"detail_style\" >"
                + desc
                + "</div></body></html>";
        try

        {
            mWebView.setVisibility(View.VISIBLE);
            mWebView.loadDataWithBaseURL("file:///android_asset/", html,
                    "text/html", "utf-8", null);
        } catch (Exception e)

        {
            // TODO: handle exception
        }
    }

    @Override
    public void onBackPressed() {
        //
        try {
            if (stack_path.size() > 1) {
                stack_path.remove(0);
                Node nd = stack_path.get(0);
                drawviews(nd);
                refreshlistview(nd);

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


    public static HomeActivity1 getInstance() {
        if (homeActivity == null) {
            homeActivity = new HomeActivity1();
        }
        return homeActivity;
    }
}
