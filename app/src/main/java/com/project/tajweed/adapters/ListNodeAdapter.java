package com.project.tajweed.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.tajweed.R;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * Created by Belgacem on 28/01/2017.
 */

public class ListNodeAdapter extends BaseAdapter {
    private final List<Node> nodes;
    private Context context;

    // private ArrayList<Nodea> nodes = new ArrayList<Nodea>();

    public ListNodeAdapter(Context context, List<Node> nodes) {
        this.context = context;
        this.nodes = nodes;
    }

    @Override
    public int getCount() {

        return nodes.size();
    }

    @Override
    public Node getItem(int position) {
        if (nodes.size() > 0)
            return nodes.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.n_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.tv_desc);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();

        }


        final Node ndRoot = nodes.get(position);
        Log.d("NodeName", ":::" + ndRoot.getNodeName()+":::" + ndRoot.getNodeValue());
        if (ndRoot.getNodeType() == Node.ELEMENT_NODE) {
            Element nodea = (Element) ndRoot;
            viewHolder.name.setText(nodea.getAttribute("name"));

        }
        return convertView;
    }

    static class ViewHolderItem {
        public TextView name;
        public TextView desc;
    }

}
