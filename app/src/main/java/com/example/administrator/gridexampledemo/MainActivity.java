package com.example.administrator.gridexampledemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<String> images;
    ArrayList<String> names,prices;


//    int images[] = {
//                        R.drawable.cartoon,R.drawable.cartoon,
//                        R.drawable.cartoon,R.drawable.cartoon,
//                        R.drawable.cartoon,R.drawable.cartoon,
//                        R.drawable.cartoon,R.drawable.cartoon,
//                        R.drawable.cartoon,R.drawable.cartoon,
//                        R.drawable.cartoon,R.drawable.cartoon
//                    };
//    String names[] = {"Cartoon","Cartoon","Cartoon","Cartoon","Cartoon","Cartoon","Cartoon","Cartoon","Cartoon","Cartoon","Cartoon","Cartoon"};
//    String prices[] = {"140","150","110","250","850","1000","110","140","150","110","250","850"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.gridview);

        new ExecuteTask().execute();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, ZoomActivity.class);
                intent.putExtra("image", images.get(i)); // put image data in Intent
                intent.putExtra("names", names.get(i)); // put image data in Intent
                intent.putExtra("price", prices.get(i)); // put image data in Intent
                startActivity(intent); // start Intent
            }
        });



    }

    public class ExecuteTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... urls) {
            try {


                HttpGet httpget = new HttpGet("http://192.168.71.109/VanHireQAAPI/get-vehiclesearchlist?VehicleTypeId=1");
                httpget.setHeader("X-Auth-Token","v#1nH!%r18_8$ky399P1@3h-iR2vn");
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpget);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                images = new ArrayList<>();
                names = new ArrayList<>();
                prices = new ArrayList<>();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject responseDataObj = new JSONObject(data);

                    JSONObject vehicleObj = responseDataObj.getJSONObject("responseData");


                    try {
                        JSONArray result = vehicleObj.getJSONArray("vehicles");
                        for(int i=0;i<result.length();i++)
                        {
                            JSONObject object = result.getJSONObject(i);

                            String imagepath = "http://192.168.71.109/VanHireQAAPI/"+object.getString("ImagePath")+object.getString("VehicleImage");
                            images.add(imagepath);
                            names.add(object.getString("VehicleName"));
                            prices.add(object.getString("SecondoryFuel"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return true;
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),images,names,prices);
            gridView.setAdapter(customAdapter);

        }
    }
}
