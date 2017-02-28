package com.project.tajweed.xmlBeam;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * Created by Belgacem on 23/02/2017.
 */

@XStreamAlias("detail")
public class Detail {
    public void setMessage(String message) {
        this.message = message;
    }

    @XStreamImplicit
    String message;
    @XStreamAlias("code")
    String code;

    public void setCode(String code) {
        this.code = code;
    }



    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
