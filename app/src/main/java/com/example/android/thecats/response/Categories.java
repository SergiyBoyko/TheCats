package com.example.android.thecats.response;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by fbrsw on 16.10.2017.
 */

@Root(name = "categories", strict = false)
public class Categories {

    @ElementList(inline = true)
    private List<Category> category;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [category = "+category+"]";
    }
}
