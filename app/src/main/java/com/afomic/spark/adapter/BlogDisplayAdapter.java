package com.afomic.spark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afomic.spark.R;
import com.afomic.spark.model.ActionListener;
import com.afomic.spark.model.BigSizeTextElement;
import com.afomic.spark.model.BlogElement;
import com.afomic.spark.model.BulletListTextElement;
import com.afomic.spark.model.GenericViewHolder;
import com.afomic.spark.model.ImageElement;
import com.afomic.spark.model.NormalSizeTextElement;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by afomic on 11/4/17.
 */

public class BlogDisplayAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private Context mContext;
    private ArrayList<BlogElement> elementList;
    public BlogDisplayAdapter(Context context, ArrayList<BlogElement> elements){
        mContext=context;
        elementList=elements;
    }
    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        switch (viewType){
            case BlogElement.Type.BIG_SIZE_TEXT:
                View v= mInflater.inflate(R.layout.item_output_big_text,parent,false);
                return new BigTextViewHolder(v);

            case BlogElement.Type.IMAGE:
                View mView= mInflater.inflate(R.layout.item_image,parent,false);
                return new ImageViewHolder(mView);
            case BlogElement.Type.NORMAL_SIZE_TEXT:
                View normalTextView= mInflater.inflate(R.layout.item_normal_text,parent,false);
                return new NormalTextHolder(normalTextView);
            case BlogElement.Type.BULLET_LIST_TEXT:
                View bullet= mInflater.inflate(R.layout.item_bullet_list,parent,false);
                return new BulletListViewHolder(bullet);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        BlogElement element=elementList.get(position);
        holder.bindView(null,position);
    }

    @Override
    public int getItemViewType(int position) {
        BlogElement element=elementList.get(position);
        return element.getType();
    }

    @Override
    public int getItemCount() {
        if(elementList==null){
            return 0;
        }
        return elementList.size();
    }
    public class ImageViewHolder extends GenericViewHolder{
        private ImageView mImageView;
        private TextView imageDescription;
        public ImageViewHolder(View itemView){
            super(itemView);
            mImageView= itemView.findViewById(R.id.imv_image);
            imageDescription=itemView.findViewById(R.id.tv_image_description);
        }
        @Override
        public void bindView(ActionListener listener, int position) {
            ImageElement element=(ImageElement) elementList.get(position);
            Glide.with(mContext)
                    .load(element.getImageUrl())
                    .thumbnail(R.drawable.image_placeholder)
                    .into(mImageView);
            imageDescription.setText(element.getImageDescription());
        }
    }
    public class NormalTextHolder extends GenericViewHolder{
        private TextView mNormalTextView;
        public NormalTextHolder(View itemView) {
            super(itemView);
            mNormalTextView= itemView.findViewById(R.id.tv_normal_text);
        }

        @Override
        public void bindView(ActionListener listener, int position) {
            NormalSizeTextElement element=(NormalSizeTextElement) elementList.get(position);
            mNormalTextView.setText(element.getBody());

        }
    }
    public class BigTextViewHolder extends GenericViewHolder {
        private TextView mBigTextView;
        public BigTextViewHolder(View itemView) {
            super(itemView);
            mBigTextView=itemView.findViewById(R.id.tv_big_text);
        }

        @Override
        public void bindView(ActionListener listener, int position) {
            BigSizeTextElement element=(BigSizeTextElement) elementList.get(position);
            mBigTextView.setText(element.getBody());
        }
    }
    public class BulletListViewHolder extends GenericViewHolder {
        private TextView mBulletTextView;
        public BulletListViewHolder(View itemView) {
            super(itemView);
            mBulletTextView=itemView.findViewById(R.id.tv_bullet);
        }

        @Override
        public void bindView(ActionListener listener, int position) {
            BulletListTextElement element=(BulletListTextElement) elementList.get(position);
            mBulletTextView.setText(element.body);

        }
    }
}
