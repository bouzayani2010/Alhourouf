package com.project.tajweed;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.tajweed.Custom.TypeWriter;
import com.project.tajweed.adapters.ListNodeAdapter;
import com.project.tajweed.adapters.ListtajweedAdapter;
import com.project.tajweed.utils.Datahelper;
import com.project.tajweed.utils.Utils;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.lucasr.twowayview.TwoWayView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bbouzaiene on 31/07/2017.
 */

public class HomeActivity1 extends Activity {

    private static Node rNode;
    private static HomeActivity1 homeActivity;
    private ListtajweedAdapter adapter;
    private static List<Section> cartList;


    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_back)
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
    private RelativeLayout treeconTainer;
    private TreeNode root;
    private String nameRoot = "";
    private Node ndRoot;
    private RelativeLayout dataview;
    private List<String> nodeList;
    private AndroidTreeView tView;
    private TextView tv_name;
    private TypeWriter webviewtypeWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        tv_name = (TextView) findViewById(R.id.tv_name);
        ButterKnife.bind(this);
        mWebView = (WebView) this.findViewById(R.id.webview);
        webviewtypeWriter=findViewById(R.id.webviewtypeWriter);
        listView = (ListView) this.findViewById(R.id.listview);
        dataview = (RelativeLayout) findViewById(R.id.dataview);
        treeconTainer = (RelativeLayout) this.findViewById(R.id.treeconTainer);
        img = (ImageView) this.findViewById(R.id.image_view);
        img.setVisibility(View.GONE);
        stack_path = new Stack();
        cartList=Datahelper.getInstance().getNodeXmlData();
        ndRoot = Datahelper.getInstance().getNodeXmlStructure();
        if (ndRoot.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) ndRoot;
            nameRoot = eElement.getAttribute("name");
            tv_name.setText(nameRoot);
        }
        stack_path.add(0, ndRoot);

        //   nodes = rNode.getNodes();


        items_path = new ArrayList<>();

        root = TreeNode.root();
        MyHolder.IconTreeItem nodeItem = new MyHolder.IconTreeItem("" + nameRoot);

        final TreeNode parentRoot = new TreeNode(nodeItem);

        parentRoot.setViewHolder(new MyHolder(HomeActivity1.this, 0));

        refreshTreeView(ndRoot, parentRoot);
        root.addChild(parentRoot);
        List<Node> stack_path1 = reformulePath(parentRoot.getPath());
        createviews(stack_path1);

        tView = new AndroidTreeView(this, root);
        tView.setDefaultNodeClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                View nodeview = node.getViewHolder().getView();
                TextView tv_name = (TextView) nodeview.findViewById(R.id.tv_name);
                // tv_name.setTextColor(getResources().getColor(R.color.colorAccent));
                if (node.isExpanded()) {

                    ImageView img_node = (ImageView) nodeview.findViewById(R.id.icon);
                    img_node.setImageResource(R.drawable.down);
                } else {

                    ImageView img_node = (ImageView) nodeview.findViewById(R.id.icon);
                    img_node.setImageResource(R.drawable.up);
                }
                List<Node> stack_path1 = reformulePath(parentRoot.getPath());
                createviews(stack_path1);
            }
        });

        //tView.setDefaultAnimation(true);
        tView.setUseAutoToggle(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom, true);
        treeconTainer.addView(tView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                tView.restoreState(state);
            }
        }

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //listView.setOnItemClickListener(this);
    }


    public void refreshTreeView(Node ndRoot, final TreeNode parent) {
        if (ndRoot.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) ndRoot;
            Log.d("NodeName", ":::" + eElement.getAttribute("name"));
            nList = ndRoot.getChildNodes();
            final List<Node> nListFiltered = Utils.filterNodes(nList);
            Log.d("NodeName", ":::" + nList.getLength());

            for (final Node nd : nListFiltered) {

                if (nd.getNodeType() == Node.ELEMENT_NODE) {
                    Element nodea = (Element) nd;

                    MyHolder.IconTreeItem nodeItem = new MyHolder.IconTreeItem("" + nodea.getAttribute("name").toString());

                    final TreeNode child1 = new TreeNode(nodeItem);
                    List<Node> stack_path1 = reformulePath(child1.getPath());


                    child1.setViewHolder(new MyHolder(HomeActivity1.this, stack_path1.size()));
                    //   nodeItem.setPaddingLevel(stack_path1.size());
                    refreshTreeView(nd, child1);


                    child1.setClickListener(new TreeNode.TreeNodeClickListener() {
                        @Override
                        public void onClick(TreeNode node, Object value) {
                            String desc = "***" + child1.getPath();
                            Log.d("nodepathnodepath", desc);

                            List<Node> stack_path1 = reformulePath(child1.getPath());

                            drawviews(stack_path1);
                            createviews(stack_path1);
                            if (node.isExpanded()) {

                                View nodeview = node.getViewHolder().getView();
                                ImageView img_node = (ImageView) nodeview.findViewById(R.id.icon);
                                img_node.setImageResource(R.drawable.down);
                            } else {

                                View nodeview = node.getViewHolder().getView();
                                ImageView img_node = (ImageView) nodeview.findViewById(R.id.icon);
                                img_node.setImageResource(R.drawable.up);
                            }
                        }
                    });

                    parent.addChild(child1);
                }

            }
        }

    }

    private List<Node> reformulePath(String path) {

        List<String> list = Arrays.asList(path.split(":"));
        List<String> items = new ArrayList<String>(list);
        nodeList = list;
        //Log.i("pathpath", "*** " + items);
        Collections.reverse(items);
        Log.i("pathpath", "*** " + items);
        ArrayList<Node> listNodePath = new ArrayList<Node>();
        items.remove(0);
        try {
            Node childselected = ndRoot;
            listNodePath.add(0, childselected);


            //for (String element : items) {
            for (int i = 0; i < items.size(); i++) {
                String element = items.get(i);

                if (childselected.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) childselected;

                    NodeList nList1 = eElement.getChildNodes();
                    List<Node> nListFiltered = Utils.filterNodes(nList1);

                    Log.i("pathpath nListFiltered", "* " + Utils.getStrings(nListFiltered));
                    Log.i("pathpath", "* " + element);
                    int indexElement = Integer.parseInt(element) - 1;
                    if (nListFiltered != null && nListFiltered.size() > 0) {
                        childselected = nListFiltered.get(indexElement);
                    }
                    listNodePath.add(0, childselected);

                }


                // System.out.println(st2.nextElement());
            }


        } catch (NumberFormatException e) {
            Log.i("pathpath", "" + e.getMessage());
        }
        return listNodePath;
    }


    private int getDeep(TreeNode child1) {
        int i = 0;
        TreeNode parent = child1;
        while (parent != null) {
            parent = parent.getParent();
            i++;
        }
        return i;
    }


    private void createviews(List<Node> stack_path) {
        //  items_path = new ArrayList(stack_path);
        items_path = stack_path;
        pathAdapter = new PathAdapter(this, stack_path);
        TwoWayView lvTest = (TwoWayView) findViewById(R.id.lvItems);
        lvTest.setAdapter(pathAdapter);
        lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
                try {

                    int position = pathAdapter.getCount() - j - 1;
                    TreeNode selectedNode = root;
                    Log.i("pathpath1", "***" + nodeList.get(position));
                    tView.collapseAll();
                    int lev = 0;
                    Collections.reverse(nodeList);
                    if (position == 0) ;
                    else {
                        for (int i = 0; i <= position; i++) {
                            lev = Integer.parseInt(nodeList.get(i)) - 1;
                            List<TreeNode> childs = selectedNode.getChildren();
                            selectedNode = childs.get(lev);
                            selectedNode.setExpanded(true);
                            tView.expandNode(selectedNode);
                        }
                    }
                    Collections.reverse(nodeList);
                    for (int i = 0; i < j; i++) {
                        items_path.remove(0);
                    }
                    createviews(items_path);
                } catch (NumberFormatException e) {

                } catch (Exception e) {

                }


            }
        });
    }




    private static Section getSectionfrompath(String path) {
        Log.d("pathpath", "" + path);
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

    private void drawviews(List<Node> stack_path) {
        // img.setVisibility(View.VISIBLE);
        //   Collections.reverse(stack_path);
        String path = Utils.drawPath(stack_path);
        Log.d("pathpath", path);
        Section section = getSectionfrompath(path);
        if (section != null) {
            String desc = section.getDesc();
            String name = section.getName();
            if (desc != null && !desc.isEmpty()) {
                dataview.setVisibility(View.VISIBLE);
            } else {
                dataview.setVisibility(View.GONE);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) dataview.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 0);
                dataview.setLayoutParams(layoutParams);
            }

            // img.setVisibility(View.VISIBLE);
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


            String html1 ="<html>\n" +
                    "\t<head>\n" +
                    "\t\t<meta charset=\"UTF-8\">\n" +
                    "\t\t\t<title></title>\n" +
                    "\t\t</head>\n" +
                    "\t\t<body>\n" +
                    "\t\t\t\t<body>\n" +
                    "\t<p>"+desc+"</p>\n" +
                    "\t\n" +
                    "\t\t\t\t\t</body>\n" +
                    "\t\t\t\t</html>\n" +
                    "\t\t\t</body>\n" +
                    "\t\t</html>";
            try

            {
                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/neorcha.ttf");
              //  txt.setTypeface(font);
               // mWebView.setVisibility(View.VISIBLE);
                webviewtypeWriter.setVisibility(View.VISIBLE);
                mWebView.loadDataWithBaseURL("file:///android_asset/", html,
                        "text/html", "utf-8", null);
                //webviewtypeWriter.setText(Html.fromHtml(html1));
                webviewtypeWriter.setCharacterDelay(0);
                webviewtypeWriter.setTextSize(18);
                webviewtypeWriter.setTypeface(font);
                webviewtypeWriter.setPadding(5, 5, 5, 5);
                webviewtypeWriter.animateText(Html.fromHtml(html1));
              //  webviewtypeWriter.animateText(Html.fromHtml(html1).toString());
            } catch (Exception e) {
                // TODO: handle exception
            }
            dataview.setVisibility(View.VISIBLE);
        } else {
            dataview.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) dataview.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            dataview.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onBackPressed() {
        //
        try {
            if (stack_path.size() > 1) {
                stack_path.remove(0);
                Node nd = stack_path.get(0);
                //  drawviews(nd);
                // refreshTreeView(nd, parentRoot);

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
