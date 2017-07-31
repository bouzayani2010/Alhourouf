package com.project.tajweed.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.tajweed.R;
import com.project.tajweed.Section;
import com.project.tajweed.xStream.Nodea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belgacem on 28/01/2017.
 */

public class ListtajweedAdapter extends BaseAdapter {
    private final List<Nodea> nodes;
    private Context context;

   // private ArrayList<Nodea> nodes = new ArrayList<Nodea>();

    public ListtajweedAdapter(Context context, List<Nodea> nodes) {
        this.context = context;
        this.nodes = nodes;
    }

    @Override
    public int getCount() {
        return nodes.size();
    }

    @Override
    public Nodea getItem(int position) {
        if (nodes.size() > 0)
            return nodes.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Nodea nodea = nodes.get(position);
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
        viewHolder.name.setText(nodea.getName());
        //viewHolder.desc.setText(nodea.getDesc());
        return convertView;
    }

    static class ViewHolderItem {
        public TextView name;
        public TextView desc;
    }

}
