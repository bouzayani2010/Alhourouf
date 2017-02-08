package com.project.tajweed;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Belgacem on 28/01/2017.
 */

public class ListAdapter extends BaseAdapter {
    private Context context;
    ;
    private ArrayList<Section> cartList = new ArrayList<Section>();

    public ListAdapter(Context context, ArrayList<Section> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Section getItem(int position) {
        if (cartList.size() > 0)
            return cartList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Section section = cartList.get(position);
        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.tv_desc);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();

        }
        viewHolder.name.setText(section.getName());
        viewHolder.desc.setText(section.getDesc());
        return convertView;
    }

    static class ViewHolderItem {
        public TextView name;
        public TextView desc;
    }

}
