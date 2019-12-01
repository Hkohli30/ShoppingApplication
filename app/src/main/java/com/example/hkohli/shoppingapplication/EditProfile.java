package com.example.hkohli.shoppingapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EditProfile extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,SenderInterface {

    private Spinner citySpinner,stateSpinner;
    private ArrayList<String> cityArrayList,stateArrayList;
    private Button updateButton;
    private EditText shopName,email,aboutShop,number;
    private String citySpinnerValue;
    private String stateSpinnerValue;

    private TextView shopname_textView,email_textView,location_textView,phone_textView;

    private String RECEIVER_URL;
    private String UPDATER_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getURLs();

        //findViewById(R.id.app_bar).setBackgroundResource(R.drawable.download);
        // Floating Button
        floatingButton();

        SenderManager senderManager = new SenderManager(this,RECEIVER_URL);
        senderManager.DELEGATE_RESPONSE = this;
        senderManager.execute("username","hk@gmail.com");

        // Spinners
        setupSpinners();
        // EditTexts
        viewsSetup();
        updateButton = (Button)findViewById(R.id.edit_profile_update_button);
        updateButton.setOnClickListener(this);

    }

    private void getURLs()
    {
        RECEIVER_URL = getResources().getString(R.string.VirtualShopReceiverURL);
        UPDATER_URL = getResources().getString(R.string.VirtualShopUpdaterURL);
    }

    @Override
    public void onClick(View v) {

        if(v == updateButton)
        {

            String values = shopName.getText().toString().trim()+"\n"+
                    email.getText().toString().trim() + "\n"+
                    aboutShop.getText().toString().trim() +"\n" +
                    ""+ number.getText().toString().trim() +"\n" +
                    ""+stateSpinnerValue + "\n"+citySpinnerValue;
            Toast.makeText(EditProfile.this, "\n"+values, Toast.LENGTH_SHORT).show();

            String shopname = shopName.getText().toString().trim();
            String email_add = email.getText().toString().trim();
            String about_shop = aboutShop.getText().toString().trim();
            String phone_no = number.getText().toString().trim();

            if(shopname.equals("") || email_add.equals("") || about_shop.equals("") || phone_no.equals(""))
            {
                Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(!Patterns.EMAIL_ADDRESS.matcher(email_add).matches())
                {
                    Toast.makeText(this, "Please Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
                }
                else if(phone_no.length() != 10 && !Patterns.PHONE.matcher(phone_no).matches())
                {
                    Toast.makeText(this, "Please Enter a valid phone number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SenderManager senderManager = new SenderManager(this,UPDATER_URL);
                    senderManager.DELEGATE_RESPONSE = this;
                    senderManager.execute("username",email_add,"password","12223",
                        "login_type","normal","shop_name","MYShOP","address",about_shop,
                            "whatsapp_no",phone_no,"state","punjab","city","jalandhar"
                    );
                    Toast.makeText(this, ""+"AllGood", Toast.LENGTH_SHORT).show();
                }

            }


        }

    }


    private void viewsSetup()
    {
        shopName = (EditText)findViewById(R.id.edit_profile_shop_name_editText);
        email = (EditText)findViewById(R.id.edit_profile_email_editText);
        aboutShop =(EditText)findViewById(R.id.edit_profile_about_shop_editText);
        number = (EditText)findViewById(R.id.edit_profile_phone_editText);

        shopname_textView = (TextView)findViewById(R.id.edit_profile_shop_name_textView);
        email_textView = (TextView)findViewById(R.id.edit_profile_email_textView);
        location_textView = (TextView)findViewById(R.id.edit_profile_location_textView);
        phone_textView = (TextView)findViewById(R.id.edit_profile_phone_texView);
    }

    private void setupSpinners()
    {
        cityArrayList = new ArrayList<>();
        stateArrayList = new ArrayList<>();

        demoMethod(cityArrayList);
        demoMethod(stateArrayList);

        stateSpinner = (Spinner)findViewById(R.id.edit_profile_state_spinner);
        citySpinner =(Spinner)findViewById(R.id.edit_profile_city_spinner);

        stateSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item
                        ,stateArrayList));

        citySpinner.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item
                        ,cityArrayList));

        // value set
        stateSpinnerValue = stateArrayList.get(0);
        citySpinnerValue = cityArrayList.get(0);

    }

    public void demoMethod(ArrayList<String> arrayList)
    {

        for(int i =0;i<5;i++)
        {
            arrayList.add("Value "+i);
        }

    }


    private void floatingButton()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (view == citySpinner)
        {
            citySpinnerValue = cityArrayList.get(position);
        }

        if (view == stateSpinner)
        {
            stateSpinnerValue = stateArrayList.get(position);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setReceivedValues(String dp, String pic, String shopname, String email, String location, String phone) {

        shopname_textView.setText(shopname);
        email_textView.setText(email);
        location_textView.setText(location);
        phone_textView.setText(phone);

        shopName.setText(shopname);
        this.email.setText(email);
        this.number.setText(phone);

        if(!dp.trim().equals("http://dealingindia.com/"))
        {

            CollapsingToolbarLayout app = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            AsyncTaskImage asyncTaskImage = new AsyncTaskImage(dp,app);
            asyncTaskImage.execute();
        }


    }

    @Override
    public void senderInterfaceResult(String output)  {
        if(output != null)
        {
            if(output.equals(""))
            {

            }
            else
            {
                try {
                    JSONObject jsonobject = new JSONObject(output.toString().trim());
                    JSONArray jsonArray = jsonobject.getJSONArray("results");
                    JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                    String RECEIVED_SHOPNAME = jsonObject2.getString("shopname");

                    if(RECEIVED_SHOPNAME != null)
                    {
                        setReceivedValues(jsonObject2.getString("dp"),jsonObject2.getString("pic"),
                                jsonObject2.getString("shopname"),jsonObject2.getString("email"),
                                jsonObject2.getString("location"),jsonObject2.getString("phone"));
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    class AsyncTaskImage extends AsyncTask<Void,Void,Bitmap>
    {
        String URL_Image;
        CollapsingToolbarLayout toolbarLayout;

        private AsyncTaskImage(String URL_Image,CollapsingToolbarLayout toolbarLayout)
        {
            this.URL_Image = URL_Image;
            this.toolbarLayout = toolbarLayout;

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = getbmpfromURL(URL_Image);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Drawable drawable = new BitmapDrawable(getResources(),bitmap);
            toolbarLayout.setBackground(drawable);
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
}
