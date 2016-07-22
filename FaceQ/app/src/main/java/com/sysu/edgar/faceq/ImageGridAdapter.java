package com.sysu.edgar.faceq;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Edgar on 2016/7/19.
 */
public class ImageGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> imagesId;

    public ImageGridAdapter(Context context, ArrayList<Integer> ss) {
        this.mContext = context;
        imagesId = ss;
    }

    @Override
    public int getCount() {
        return imagesId.size();
    }

    @Override
    public Object getItem(int position) {
        return BitmapFactory.decodeResource(mContext.getResources(), imagesId.get(position));
    }

    @Override
    public long getItemId(int position) {
        return imagesId.get(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ImageView imageView;

        if (convertView == null) {
            WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
            int w = wm.getDefaultDisplay().getWidth();
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new AbsListView.LayoutParams((w - 3) / 3,
                    200));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imageView = (ImageView)convertView;
        }
        imageView.setImageResource(imagesId.get(position));
        int padding = 20;
        imageView.setPadding(padding, padding, padding, padding);
        return imageView;
    }

}
