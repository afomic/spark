package com.afomic.spark.model;

/**
 * Created by afomic on 11/4/17.
 */

public class NormalSizeTextElement implements BlogElement{
    private String body;

    public NormalSizeTextElement(String body){
        this.body=body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int getType() {
        return Type.NORMAL_SIZE_TEXT;
    }

    @Override
    public String toHtml() {
        return null;
    }
}
