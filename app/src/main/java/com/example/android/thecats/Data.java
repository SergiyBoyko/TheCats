package com.example.android.thecats;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class Data {
    private Images images;

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
