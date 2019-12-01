package com.example.hkohli.shoppingapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hkohli on 9/11/2016.
 */
public class StringEncoder {

    public String sendPostRequest(String requestURL, HashMap<String,String> postDataParams)
    {
        URL url;
        String response = "";

        try {
            url = new URL(requestURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setReadTimeout(15000);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            bufferedWriter.write(getPostDataParams(postDataParams));

            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            int responsecode = con.getResponseCode();

            if(responsecode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                while((response = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(response+"\n");
                }

                response = stringBuilder.toString().trim();
                return response;

            }
            else
            {
                response = null;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return  response;
    }

    private String getPostDataParams(HashMap<String,String> params) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        for(Map.Entry<String,String> entry : params.entrySet())
        {
            if(first)
            {
                first = false;
            }
            else
            {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }

        return result.toString();

    }

}
