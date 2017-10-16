package com.example.android.thecats.response;

import org.simpleframework.xml.Element;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class Data {
    @Element(required = false)
    private Images images;

    @Element(required = false)
    private Categories categories;

    public Categories getCategories ()
    {
        return categories;
    }

    public void setCategories (Categories categories)
    {
        this.categories = categories;
    }

    public Images getImages ()
    {
        return images;
    }

    public void setImages (Images images)
    {
        this.images = images;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [images = "+images+"]";
    }

}
