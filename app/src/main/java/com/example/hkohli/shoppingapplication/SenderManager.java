package com.example.hkohli.shoppingapplication;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

/**
 * Created by Hkohli on 9/10/2016.
 */
public class SenderManager extends AsyncTask<String,Void,String> {

    private Context context;
    private String PAGE_URL;
    private ProgressDialog progressDialog;
    private String title,msg;
    StringEncoder stringEncoder = new StringEncoder();

    public SenderInterface DELEGATE_RESPONSE = null;

    public SenderManager(Context context,String register_url)
    {
        this.context = context;
        this.PAGE_URL = register_url;

        title = "Processing Operation";
        msg = "Patience is the key";
    }

    public SenderManager(Context context,String register_url,String title,String msg)
    {
        this.context = context;
        this.PAGE_URL = register_url;
        this.title = title;
        this.msg = msg;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,title,msg,true,true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        DELEGATE_RESPONSE.senderInterfaceResult(""+s);
    }

    @Override
    protected String doInBackground(String... params) {

        HashMap<String,String> data = new HashMap<>();

        for(int i=0;i<params.length;i=i+2)
        {
            data.put(params[i],params[i+1]);
        }

        String result = stringEncoder.sendPostRequest(PAGE_URL,data);

        return result;
    }

}
