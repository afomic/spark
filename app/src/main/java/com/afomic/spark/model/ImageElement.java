package com.afomic.spark.model;

/**
 * Created by afomic on 11/4/17.
 */

public class ImageElement implements BlogElement {
    private String imageUrl;
    private String imageDescription;

    public ImageElement(String url){
        imageUrl=url;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int getType() {
        return Type.IMAGE;
    }

    @Override
    public String toHtml() {
        return null;
    }
}
