package com.example.android.thecats.Category;

/**
 * Created by fbrsw on 16.10.2017.
 */

public class Data {
    private Categories categories;

    public Categories getCategories ()
    {
        return categories;
    }

    public void setCategories (Categories categories)
    {
        this.categories = categories;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [categories = "+categories+"]";
    }
}
