package com.project.tajweed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Belgacem on 29/01/2017.
 */

public class MyHolderExpanded extends TreeNode.BaseNodeViewHolder<MyHolderExpanded.IconTreeItemExpanded> {

    public MyHolderExpanded(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItemExpanded value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.node_item, null, false);
        TextView tvValue = (TextView) view.findViewById(R.id.tv_name);
        tvValue.setText(value.text);

        return view;
    }

    public static class IconTreeItemExpanded {
        public int icon;
        public String text;
    }
}
