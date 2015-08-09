package com.udacity.nano.movie;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private final List<String> mImagesUrls;

    public ImageAdapter(Context context) {
        mContext = context;
        mImagesUrls = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mImagesUrls.size();
    }

    @Override
    public String getItem(int position) {
        return mImagesUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(mContext).
                load(getItem(position)).
                into(imageView);
        return imageView;
    }

    public void clear() {
        mImagesUrls.clear();
    }

    public void add(String url) {
        mImagesUrls.add(url);
    }
}
