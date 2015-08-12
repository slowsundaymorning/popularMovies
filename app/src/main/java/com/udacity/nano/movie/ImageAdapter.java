package com.udacity.nano.movie;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ImageAdapter extends BaseAdapter {

    private static final String LOG_TAG = ImageAdapter.class.getSimpleName();

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

    private final static String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/%s";
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        if(convertView == null)
            imageView = new ImageView(mContext);
        String url = getItem(position);
        Log.d(LOG_TAG, String.format("Poster Url %d : %s", position, url));
        final int targetWidth = parent.getWidth()/2;
        Transformation transformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                Log.d(LOG_TAG, String.format("source width: %d, height: %d, target width : %d, height: %d",
                        source.getHeight() , source.getWidth(),
                        targetWidth, targetHeight ));
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }
            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
        Picasso.with(mContext).
                load(String.format(BASE_POSTER_URL, getItem(position))).
                transform(transformation).
//                resize(500,800).
//                centerInside().
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
