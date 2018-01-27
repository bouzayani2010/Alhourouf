package com.project.tajweed;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.tajweed.xStream.Nodea;
import com.project.tajweed.xStream.Rnode;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bbouzaiene on 04/08/2017.
 */

public class PathAdapter extends BaseAdapter {
    private final Context mContext;
    private List<Node> items_path = new ArrayList<Node>();


    public PathAdapter(Context mContext, List items_path) {
        this.mContext = mContext;
        this.items_path = items_path;
    }

    @Override
    public int getCount() {
        return items_path.size();
    }

    @Override
    public Node getItem(int position) {
        if (items_path.size() > 0)
            return items_path.get(position);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = items_path.get(position);
        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();

        }
        try {
            if (node.getNodeType() == Node.ELEMENT_NODE) {


                Element nodea = (Element) node;

                viewHolder.name.setText(nodea.getAttribute("name"));
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            viewHolder.name.setText(((Nodea) node).getName());
        }
        return convertView;
    }

    static class ViewHolderItem {
        public TextView name;
    }
}
