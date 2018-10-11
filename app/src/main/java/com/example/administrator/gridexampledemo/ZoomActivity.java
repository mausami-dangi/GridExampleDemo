package com.example.administrator.gridexampledemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ZoomActivity extends AppCompatActivity {

    TextView nameTV;
    ImageView selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_item_zoom);

        selectedImage = (ImageView) findViewById(R.id.selectedImage); // init a ImageView
        nameTV = (TextView)findViewById(R.id.nameTV);


        nameTV.setText(getIntent().getStringExtra("names"));


        String imagePath = getIntent().getStringExtra("image");

        Picasso.with(ZoomActivity.this).load(imagePath).into(selectedImage);

    }
}
