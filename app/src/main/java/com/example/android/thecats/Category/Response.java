package com.example.android.thecats.Category;

/**
 * Created by fbrsw on 16.10.2017.
 */

public class Response {
    private Data data;

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
