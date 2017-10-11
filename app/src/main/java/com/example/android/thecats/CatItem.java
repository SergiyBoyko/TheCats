package com.example.android.thecats;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class CatItem {
    private String imageTitle;
    private String imageUrl;
    private Integer imageId;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public Integer getImageId() {
        return imageId;
    }
}
