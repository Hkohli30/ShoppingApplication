package com.example.hkohli.shoppingapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyShop extends AppCompatActivity implements SenderInterface {


    private RecyclerView recyclerView;
    private TextView shop_name,location,email,products;
    private ArrayList<ProductInformation> arrayList;
    private String SHOP_URL;
    private CollapsingToolbarLayout COVER_IMAGE;
    private FloatingActionButton DP_IMAGE;
    private RVAdapter rvAdapter;
    private de.hdodenhof.circleimageview.CircleImageView roundImageView;
    private String SHOP_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // CATCH INTENT
        Intent intent = getIntent();
        SHOP_TYPE = intent.getStringExtra("SHOP_TYPE");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // Getting id's

        shop_name = (TextView)findViewById(R.id.Myshop_shopname);
        location = (TextView)findViewById(R.id.Myshop_location);
        email = (TextView)findViewById(R.id.Myshop_email);
        products =(TextView)findViewById(R.id.Myshop_products);
        COVER_IMAGE = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        DP_IMAGE = (FloatingActionButton)findViewById(R.id.fab);

        // GETTING DATA FROM URL
        SHOP_URL = getResources().getString(R.string.SellerDashboardURL);
        SenderManager senderManager = new SenderManager(this,SHOP_URL,"Receving Data..","Getting Data from server");
        senderManager.DELEGATE_RESPONSE = this;
        senderManager.execute("email","admin@dealingindia.com");

        // findViewById(R.id.toolbar_layout).setBackgroundResource(R.drawable.camera_big_image);

        initilizeRecyclerView();
    }

    public void initilizeList(JSONArray jsonArray) throws JSONException {

        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String image_url = jsonObject.getString("image");
            String details = jsonObject.getString("title");
            String price = "Rs."+jsonObject.getString("sp");
            String location = jsonObject.getString("state");
            String condition = jsonObject.getString("condition");
            String product_id = jsonObject.getString("id");
            arrayList.add(new ProductInformation(image_url,details,price,location,condition,product_id));
        }
        rvAdapter.notifyDataSetChanged();
    }

    private void initilizeRecyclerView()
    {
        recyclerView = (RecyclerView)findViewById(R.id.Myshop_recyclerview);
        arrayList = new ArrayList();
        PreCatchingLayoutManager layoutManager = new PreCatchingLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        layoutManager.setExtraLayoutSpace(layoutManager.heightSetter(this));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        rvAdapter = new RVAdapter(arrayList);
        rvAdapter.typeSetter("shop");
        rvAdapter.shopTYPE(SHOP_TYPE);
        recyclerView.setAdapter(rvAdapter);

        recyclerView.setItemViewCacheSize(12);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);

        shop_name.getParent().requestChildFocus(shop_name,shop_name);
    }

    @Override
    public void senderInterfaceResult(String output) {
        if(output != null)
        {
            try {
                parseJSONData(output.trim());
                Log.i("JSONData",output.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseJSONData(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData).getJSONObject("results");
        JSONArray shop_jsonArray = jsonObject.getJSONArray("shop_image");
        JSONArray product_jsonArray = jsonObject.getJSONArray("product_data");
        Log.i("checker",shop_jsonArray.toString());
        // Get Images
        JSONObject jsonObject1 = shop_jsonArray.getJSONObject(0);
        AsyncTaskImage asyncImageManager = new AsyncTaskImage(jsonObject1.getString("cover_image"),COVER_IMAGE);
        asyncImageManager.execute();

        roundImageView = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.round_sample);

        AsyncImageManager asyncImageManager1 = new AsyncImageManager(jsonObject1.getString("profile_image"),roundImageView);
        asyncImageManager1.execute();


        initilizeList(product_jsonArray);
    }

    // MENU'// STOPSHIP: 1/21/2017
     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         if(SHOP_TYPE.equals("MY")) {
             getMenuInflater().inflate(R.menu.my_shop_options_menu, menu);
             return true;
         }
         else
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.myshop_edit_profile && SHOP_TYPE.equals("MY")) {
            startActivity(new Intent(this, EditProfile.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
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
