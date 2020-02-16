package com.example.diana.licentaschelet.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

public class ImagesDownload extends AsyncTask<String, Void, Bitmap> {
    String url;

    public ImagesDownload(String url) {
        this.url = url;
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmap=null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error downloading image", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }


}