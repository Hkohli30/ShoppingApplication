package com.example.hkohli.shoppingapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hkohli on 1/18/2017.
 */
class AsyncImageManager extends AsyncTask<Void,Void,Bitmap> {
    String imageURL;
    ImageView imageView;

    public AsyncImageManager(String imageURL,ImageView imageView) {
        this.imageURL = imageURL;
        this.imageView = imageView;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap bitmap = getbmpfromURL(imageURL);
        return bitmap;
    }


    public Bitmap getbmpfromURL(String surl) {
        try {
            java.net.URL url = new URL(surl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.connect();
            InputStream in = urlcon.getInputStream();
            Bitmap mIcon = BitmapFactory.decodeStream(in);
            return mIcon;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
