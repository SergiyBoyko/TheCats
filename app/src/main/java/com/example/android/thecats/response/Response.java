package com.example.android.thecats.response;

import org.simpleframework.xml.Element;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class Response {

    @Element(required = false)
    private Apierror apierror;

    @Element(required = false)
    private Data data;

    public Apierror getApierror() {
        return apierror;
    }

    public void setApierror(Apierror apierror) {
        this.apierror = apierror;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }

}
