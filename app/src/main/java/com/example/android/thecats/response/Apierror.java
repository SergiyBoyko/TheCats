package com.example.android.thecats.response;

import org.simpleframework.xml.Element;

/**
 * Created by fbrsw on 17.10.2017.
 */

public class Apierror {
    @Element(name = "message", required = false)
    private String message;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+"]";
    }

}
