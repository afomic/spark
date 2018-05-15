package com.afomic.spark.model;

import android.os.Parcel;

/**
 * Created by afomic on 11/4/17.
 */

public class BulletListTextElement implements BlogElement {
    public static final Creator<BulletListTextElement> CREATOR = new Creator<BulletListTextElement>() {
        @Override
        public BulletListTextElement createFromParcel(Parcel in) {
            return new BulletListTextElement(in);
        }

        @Override
        public BulletListTextElement[] newArray(int size) {
            return new BulletListTextElement[size];
        }
    };
    public String body;

    protected BulletListTextElement(Parcel in) {
        body = in.readString();
    }

    public BulletListTextElement() {
        this.body = "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int getType() {
        return Type.BULLET_LIST_TEXT;
    }

    @Override
    public String toHtml() {
        return "<bl>" + body + "</bl>";
    }
}
