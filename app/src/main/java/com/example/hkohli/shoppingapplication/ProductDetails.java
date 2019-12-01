package com.example.hkohli.shoppingapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetails extends AppCompatActivity implements SenderInterface,View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView RELATED_PRODUCTS_RECYCLERVIEW;
    private ArrayList<ProductInformation> RELATED_PRODUCTS_ARRAYLIST;
    private FeedRVAdapter RELATED_PRODUCTS_ADAPTER;
    private ArrayList<String> imageArrayList;
    private ImageAdapter imageAdapter;
    // Alert dialog data
    private AlertDialog alertDialog;
    private Button postButton;
    private EditText email_edit,phone_edit,descp_edit;

    // intent data and receiver url
    private String RECEIVING_URL;
    private String PRODUCT_ID;

    // PRODUCT DETAILS
    private TextView PRODUCT_TITLE,PRODUCT_PRICE,PRODUCT_CONDITION,PRODUCT_VIEWS,PRODUCT_DESCP,
            PRODUCT_PAYMENT,PRODUCT_DELIVERY;
    private TextView SELLER_SHOP_NAME,SELLER_EMAIL,SELLER_LOCATION,SELLER_PRODUCTS_QUANTITY;
    private CircleImageView SELLER_IMAGEVIEW;
    private String SELLER_MOBILE = "";

    private Button buttonContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Getting the intent
        Intent intent = getIntent();
        PRODUCT_ID = intent.getStringExtra("PRODUCT_ID");
        RECEIVING_URL = getRecevingURL();

        // PRODUCT DETAILS ID'S
        initilizeID();
        floatingButtonManager();
        manageGridView();

        // Getting JSON Value
        SenderManager senderManager = new SenderManager(this,RECEIVING_URL,"","");
        senderManager.DELEGATE_RESPONSE = this;
        senderManager.execute("id",PRODUCT_ID);

        // Call button function
        buttonContact = (Button)findViewById(R.id.product_details_contact_button);
        if (buttonContact != null) {
            buttonContact.setOnClickListener(this);
        }

        postButton = (Button)findViewById(R.id.product_details_postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }



    public void manageGridView()
    {
        imageArrayList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.product_details_imageview);
        imageAdapter = new ImageAdapter(imageArrayList,this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imageAdapter);

        RELATED_PRODUCTS_RECYCLERVIEW = (RecyclerView)findViewById(R.id.product_details_related_recyclerview);
        RELATED_PRODUCTS_ARRAYLIST = new ArrayList<>();
        RELATED_PRODUCTS_ADAPTER = new FeedRVAdapter(RELATED_PRODUCTS_ARRAYLIST);
        PreCatchingLayoutManager layoutManager = new PreCatchingLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        layoutManager.setExtraLayoutSpace(layoutManager.witdhSetter(this));
        RELATED_PRODUCTS_RECYCLERVIEW.setLayoutManager(layoutManager);
        RELATED_PRODUCTS_RECYCLERVIEW.setAdapter(RELATED_PRODUCTS_ADAPTER);

    }

    public void floatingButtonManager()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "Contact Seller", Snackbar.LENGTH_LONG)
                        .setAction("CALL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                View snackbar_view = snackbar.getView();
                snackbar_view.setBackgroundColor(Color.WHITE);
                TextView textView = (TextView)snackbar_view.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.RED);
                snackbar.show();
            }
        });
    }

    private String getRecevingURL()
    {
        return getResources().getString(R.string.ProductPageURL);
    }

    @Override
    public void senderInterfaceResult(String output) {
        if(output != null)
        {
            try {
                parseJSONData(output);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseJSONData(String json_string) throws JSONException {
        JSONObject jsonObject = new JSONObject(json_string).getJSONObject("results");
        JSONArray seller_info_json = jsonObject.getJSONArray("seller_information");
        JSONArray product_details_json = jsonObject.getJSONArray("product");
        JSONArray related_products_json = jsonObject.getJSONArray("related_products");
        // JSONArray related_products = jsonObject.getJSONArray("related_products");

        // SETTING THE VALUES TO THE CORRESPONDING DATA FIELDS
        JSONObject jsonObject1 = product_details_json.getJSONObject(0);
        PRODUCT_TITLE.setText(jsonObject1.getString("title"));
        PRODUCT_PRICE.setText("Rs. "+ jsonObject1.getString("sp"));
        PRODUCT_CONDITION.setText(jsonObject1.getString("condition"));
        PRODUCT_VIEWS.setText(jsonObject1.getString("view"));
        PRODUCT_DESCP.setText(jsonObject1.getString("descp"));
        PRODUCT_PAYMENT.setText(jsonObject1.getString("cash_type"));
        PRODUCT_DELIVERY.setText(jsonObject1.getString("delivery_type"));
        SELLER_EMAIL.setText(jsonObject1.getString("email"));
        SELLER_LOCATION.setText(jsonObject1.getString("state"));
        SELLER_PRODUCTS_QUANTITY.setText("02");
        SELLER_MOBILE = jsonObject1.getString("mobile");

        JSONObject jsonObject2 = seller_info_json.getJSONObject(0);
        SELLER_SHOP_NAME.setText(jsonObject2.getString("shop_name"));
        AsyncImageManager asyncImageManager = new AsyncImageManager(jsonObject2.getString("dp"),SELLER_IMAGEVIEW);
        asyncImageManager.execute();

        loadImages(product_details_json);
        loadRelatedProducts(related_products_json);

    }

    private void loadImages(JSONArray jsonArray) throws JSONException {
        JSONObject jsonObject = jsonArray.getJSONObject(0);

            if(!imageArrayList.isEmpty())
                imageArrayList.clear();
        for(int i =1;i<=3;i++)
        {
            String image_url = jsonObject.getString("media_" + i);
            if(!image_url.equals("http://dealingindia.com/mobile/"))
            {
                imageArrayList.add(image_url);
            }
        }

        imageAdapter.notifyDataSetChanged();
    }

    private void loadRelatedProducts(JSONArray jsonArray) throws JSONException
    {
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String image_url = jsonObject.getString("image");
            String details = jsonObject.getString("title");
            String price = "Rs."+jsonObject.getString("sp");
            String location = jsonObject.getString("state");
            String condition = jsonObject.getString("condition");
            String product_id = jsonObject.getString("id");
            RELATED_PRODUCTS_ARRAYLIST.add(new ProductInformation(image_url,details,price,location,condition,product_id));
        }
        RELATED_PRODUCTS_ADAPTER.notifyDataSetChanged();
    }

    private void initilizeID()
    {
        PRODUCT_TITLE = (TextView)findViewById(R.id.product_details_salesline);
        PRODUCT_PRICE = (TextView)findViewById(R.id.product_details_price);
        PRODUCT_CONDITION = (TextView)findViewById(R.id.product_details_condition_type);
        PRODUCT_VIEWS = (TextView)findViewById(R.id.product_details_views);
        PRODUCT_DESCP = (TextView)findViewById(R.id.product_details_description);
        PRODUCT_PAYMENT = (TextView)findViewById(R.id.product_details_payment_method);
        PRODUCT_DELIVERY = (TextView)findViewById(R.id.product_details_delivery_method);

        SELLER_EMAIL = (TextView)findViewById(R.id.product_details_domain_name);
        SELLER_LOCATION = (TextView)findViewById(R.id.product_details_place);
        SELLER_SHOP_NAME = (TextView)findViewById(R.id.product_details_shop_name);
        SELLER_PRODUCTS_QUANTITY = (TextView)findViewById(R.id.product_details_no_of_products);
        SELLER_IMAGEVIEW = (CircleImageView) findViewById(R.id.product_details_seller_imageview);

        SELLER_IMAGEVIEW.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v == buttonContact)
        {
//            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + SELLER_MOBILE));
//            try{
//                startActivity(intent);
//            }
//            catch(android.content.ActivityNotFoundException e)
//            {
//                Toast.makeText(ProductDetails.this, ""+"Error Finding Dialer", Toast.LENGTH_SHORT).show();
//            }

        }

        if(v == SELLER_IMAGEVIEW)
        {
            Intent intent = new Intent(this,MyShop.class);
            intent.putExtra("SHOP_TYPE","others");
            startActivity(intent);
        }

    }
}
