package com.example.android.thecats.Entity;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by fbrsw on 11.10.2017.
 */

@Root(name = "images", strict = false)
public class Images {

    @ElementList(inline = true, required = false)
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    //    public Image getImage ()
//    {
//        return image;
//    }
//
//    public void setImage (Image image)
//    {
//        this.image = image;
//    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+images+"]";
    }

}
