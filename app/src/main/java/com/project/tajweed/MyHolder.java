package com.project.tajweed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Belgacem on 29/01/2017.
 */

public class MyHolder extends TreeNode.BaseNodeViewHolder<MyHolder.IconTreeItem>{

    public MyHolder(Context context) {
        super(context);
    }


    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.node_item, null, false);
        TextView tvValue = (TextView) view.findViewById(R.id.tv_name);
        ImageView ivValue = (ImageView) view.findViewById(R.id.icon);
        tvValue.setText(value.text);
        if(node.isExpanded()){
            ivValue.setImageResource(R.drawable.up);
        }
        else{
            ivValue.setImageResource(R.drawable.down);
        }

        return view;
    }

    public static class IconTreeItem {
        public int icon;
        public String text;
        public Section section;

        public IconTreeItem(String name) {
            this.text=name;
        }

        public IconTreeItem() {

        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
