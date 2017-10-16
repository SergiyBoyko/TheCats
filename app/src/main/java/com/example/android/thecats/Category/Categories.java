package com.example.android.thecats.Category;

/**
 * Created by fbrsw on 16.10.2017.
 */

public class Categories {
    private Category[] category;

    public Category[] getCategory ()
    {
        return category;
    }

    public void setCategory (Category[] category)
    {
        this.category = category;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [category = "+category+"]";
    }
}
