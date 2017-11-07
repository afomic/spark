package com.afomic.spark.util;

import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;

import com.afomic.spark.data.Constants;

/**
 * Created by afomic on 04-Nov-16.
 *
 */
public class TextUtil {
    public static CharSequence highlightText(int start,int end,String text){
        String startFont="<font color=\"#ff5722\">";
        String closeFont="</font>";
        StringBuilder builder=new StringBuilder(text);
        builder.insert(start,startFont);
        //the end would have shifted by the lenght of the startFont
        int closeEnd=end +startFont.length();
        builder.insert(closeEnd,closeFont);
        Spanned spanText= Html.fromHtml(builder.toString());
        double stringLength=spanText.length();
        //get the length of the text after the highlighted text
        double after=stringLength-end;
        //get the ratio of the text before the highlighted text,and after the text
        double ratioX=start/stringLength;
        double ratioY=after/stringLength;
        //use the ratio to  get the length of text before and after the  highlighted text
        int stringStart=(int) (ratioX*200);
        int stringEnd=(int) (ratioY*200);
        int offsetStart=start-stringStart;
        int offsetEnd=end+stringEnd;
        Log.d(Constants.TAG,"Start: "+start+"End: "+end);
        if(stringLength>230){
            return spanText.subSequence(offsetStart,offsetEnd);
          /*since we cannot show all the content of the constitution on the search card
          * we add start to show the text continues
            */

        }
        return spanText.subSequence(0,(int) stringLength);
    }public static Spannable highLight(int start,int end,CharSequence word){
        SpannableStringBuilder spanText=new SpannableStringBuilder(word);
        spanText.setSpan(new BackgroundColorSpan(Color.argb(173, 251, 244, 94)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanText;
    }
    public static Spannable highlightSection(int start,int end,String word){
        Spannable spanText=Spannable.Factory.getInstance().newSpannable(word);
        spanText.setSpan(new BackgroundColorSpan(Color.argb(173, 251, 244, 94)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanText;
    }


}
