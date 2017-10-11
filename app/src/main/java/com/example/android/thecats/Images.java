package com.example.android.thecats;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class Images {
    private Image[] image;

    public Image[] getImage ()
    {
        return image;
    }

    public void setImage (Image[] image)
    {
        this.image = image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+"]";
    }

}
