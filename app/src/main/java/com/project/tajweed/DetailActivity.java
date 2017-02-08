package com.project.tajweed;

/**
 * Created by Belgacem on 28/01/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DetailActivity extends Activity {

    private static DetailActivity dt;
    public static String path;
    public static String desc;
    public static String name;
    private TextView tvpath;
    // private TextView tvdetail;
    private TextView tvname;
    private LinearLayout path_layout;
    List path_string;
    private WebView mWebView;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        tvpath = (TextView) this.findViewById(R.id.tv_path);
        tvname = (TextView) this.findViewById(R.id.tv_name);
        path_layout = (LinearLayout) this.findViewById(R.id.paths);

        mWebView = (WebView) this.findViewById(R.id.webview);
        img = (ImageView) this.findViewById(R.id.image_view);
        img.setVisibility(View.VISIBLE);
        path_string = new ArrayList();
        //  path = path.replace("/", " > ");
        Log.d("nodenode", ":" + path);

        createPaths();

        tvpath.setText(path);

        tvname.setText(name);
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
        }else if (name.equals("12 - من طرف اللسان و من فوق الثنايا السفلى")) {
            img.setImageResource(R.drawable.al_safeer_12);
        }else if (name.equals("13 - من طرف اللسان و لكن بينه و بين الثنايا العليا")) {
            img.setImageResource(R.drawable.taa_daal_ta_11);
        }else if (name.equals("14 - من بطن الشفة مع أطراف الثنايا المشرفة")) {
            img.setImageResource(R.drawable.al_faa_14);
        }else if (name.equals("الشفتان")) {
            img.setImageResource(R.drawable.al_waw_15);
        }else if (name.equals("15- من بين الشفتين لكنها داخلة إلى الداخل")) {
            img.setImageResource(R.drawable.al_waw_15);
        }else if (name.equals("16 - من بين الشفتين لكنها من الخارج")) {
            img.setImageResource(R.drawable.meem_baa_16);
        }else if (name.equals("الخيشوم")) {
            img.setImageResource(R.drawable.khayshoom_17);
        }



        else {
            img.setVisibility(View.GONE);
        }


        String html = "<html dir=\"rtl\" lang=\"\"><head><style>@font-face {font-family: framd;src: " +
                "url('file:///android_asset/fonts/framd.ttf');}.container_style {margin: 0; padding" +
                ": 10px 5px 10px 5px;color: #aaa9a7;font-size: 20px;font-weight:normal;font-family: " +
                "'framd';}.detail_style {color: #aaa9a7;font-size: 20px;font-weight:normal;}</style>" +
                "</head><body class=\"container_style\"><div class=\"detail_style\" >"
                + desc
                + "</div></body></html>";
        try

        {
            mWebView.setVisibility(View.VISIBLE);
            mWebView.loadDataWithBaseURL("file:///android_asset/", html,
                    "text/html", "utf-8", null);
        } catch (
                Exception e
                )

        {
            // TODO: handle exception
        }
        //    tvdetail.setText(desc);
    }

    private void createPaths() {
        StringTokenizer stringTokenizer = new StringTokenizer(path, "/");

        while (stringTokenizer.hasMoreElements()) {
            final String nodename = stringTokenizer.nextElement().toString();
            Log.d("nodenode", ":" + nodename);
            path_string.add(nodename);

        }
        createviews();
    }

    private void createviews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < path_string.size(); i++) {
            final String nodename = path_string.get(i).toString();
            final TextView pathitem = (TextView) inflater.inflate(R.layout.path_item, null, false);
            pathitem.setText(nodename);
            path_layout.addView(pathitem, 0);
            pathitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("nodenode 1", ":" + pathitem.getText().toString());
                    refresh_Paths(pathitem.getText().toString());
                    refresh_List(pathitem.getText().toString());
                }
            });
        }
    }

    private void refresh_Paths(String s) {
        List path_string1 = path_string;
        for (int i = 0; i < path_string.size(); i++) {
            if (s.equals((String) path_string.get(i))) {
                Log.d("nodenode " + i, (String) path_string.get(i));
            } else {
                path_string1.remove(i);
            }

        }
    }

    private void refresh_List(String s) {
        ;
    }

    public void setpath(String path) {
        this.path = path;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static DetailActivity newInstance() {
        if (dt == null) {
            dt = new DetailActivity();
        }
        return dt;

    }
}
