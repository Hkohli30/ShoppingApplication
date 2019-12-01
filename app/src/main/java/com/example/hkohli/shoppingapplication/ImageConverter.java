package com.example.hkohli.shoppingapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Hkohli on 9/22/2016.
 */
public class ImageConverter {

    public String bitmapToBase64(Image image,Context context)
    {
        String stringImage = null;
        Bitmap bitmap;


        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.l);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        stringImage =  Base64.encodeToString(byteArray, Base64.DEFAULT);

        return stringImage;
    }


    public Bitmap base64ToBitmap(String b64)
    {
        byte imageAsByte[] = Base64.decode(b64.getBytes(),Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(imageAsByte,0,imageAsByte.length);
    }

}
