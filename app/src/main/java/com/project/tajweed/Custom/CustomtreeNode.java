package com.project.tajweed.Custom;

import com.unnamed.b.atv.model.TreeNode;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class CustomtreeNode {
    private String name= "";
    private Node node;
    private CustomtreeNode mParent;
    private int mId;
    private int mLastId;



    public CustomtreeNode addChild1(CustomtreeNode childNode) {
        childNode.mParent = this;
        childNode.mId = generateId();
        return this;
    }




    private int generateId() {
        return ++mLastId;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }
}