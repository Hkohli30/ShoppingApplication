package com.example.hkohli.shoppingapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hkohli on 9/10/2016.
 */
public class JSONGetterClass extends AsyncTask<String,Void,String> {

    ProgressDialog progressDialog;
    Context context;
    public JSONInterface delegate = null;

    public JSONGetterClass(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context,"Retreiving Data from Database",
                        "ONLINE CONNECTOR",true,true);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        try
        {
            delegate.jsonProcessFinished(s);
        }
        catch(JSONException e)
        {
            Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... params) {

        String uri = params[0];
        BufferedReader bufferedReader = null;

        try
        {
            URL url = new URL(uri);
            HttpURLConnection con =(HttpURLConnection)url.openConnection();
            con.setReadTimeout(60000);

            StringBuilder stringBuilder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;

            while((json = bufferedReader.readLine())!= null && json.length() != 0)
            {
                stringBuilder.append(json);
            }

            return stringBuilder.toString().trim();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
