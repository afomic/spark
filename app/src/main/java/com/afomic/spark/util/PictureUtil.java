package com.afomic.spark.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by afomic on 14-Aug-16.
 */
public class PictureUtil {
    /**
     * Get a BitmapDrawable from a local file that is scaled down
     * to fit the current Window size.
     */
    @SuppressWarnings("deprecation")
    public static Bitmap getImage(Context a,String name){
        AssetManager assetManager=a.getAssets();
        InputStream stream=null;
        try{
            stream=assetManager.open(name+".jpg");
        }catch (IOException e){
            e.printStackTrace();
            return null;

        }
        return BitmapFactory.decodeStream(stream);
    }
    public static Bitmap getSemesterImage(Context a,int level,int semester){
        AssetManager assetManager=a.getAssets();
        InputStream stream=null;
        try{
            stream=assetManager.open(""+level+semester+".jpg");
        }catch (IOException e){
            e.printStackTrace();
            return null;

        }
        return BitmapFactory.decodeStream(stream);
    }



}
