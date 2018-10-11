package com.example.administrator.gridexampledemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class CustomAdapter extends BaseAdapter {

    Context context;
//    int images[];
//    String names[];
//    String prices[];
    LayoutInflater inflater;
    ArrayList<String> images;
    ArrayList<String> names, prices;

    public CustomAdapter(Context applicationContext, ArrayList<String> images, ArrayList<String> names, ArrayList<String> prices) {
        this.context = applicationContext;
        this.images = images;
        this.names = names;
        this.prices = prices;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int j, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.grid_item,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageview);
        TextView imageNames = (TextView)view.findViewById(R.id.imageNameTV);
        TextView imagePrices = (TextView)view.findViewById(R.id.imagePriceTV);

        for (int i = 0; i<names.size(); i++) {
            Picasso.with(context).load(images.get(j)).into(imageView);
            imageNames.setText(names.get(j));
            imagePrices.setText(prices.get(j));
        }
        return view;
    }


}
