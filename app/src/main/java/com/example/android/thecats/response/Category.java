package com.example.android.thecats.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by fbrsw on 16.10.2017.
 */

@Root(name = "category", strict = false)
public class Category {

    @Element(name = "id")
    private String id;

    @Element(name = "name")
    private String name;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", name = "+name+"]";
    }
}
