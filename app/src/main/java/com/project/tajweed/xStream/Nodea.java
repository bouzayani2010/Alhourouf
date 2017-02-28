package com.project.tajweed.xStream;

import com.project.tajweed.xmlBeam.Detail;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belgacem on 23/02/2017.
 */

@XStreamAlias("node")
public class Nodea {



    @XStreamAlias("name")
    String name;
    @XStreamImplicit
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
