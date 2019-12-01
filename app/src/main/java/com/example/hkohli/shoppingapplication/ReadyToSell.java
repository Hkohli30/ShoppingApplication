package com.example.hkohli.shoppingapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ReadyToSell extends AppCompatActivity implements View.OnClickListener {

    private Spinner category,sub_category;
    private ArrayList<String> category_array,sub_category_array;
    private ImageButton button_1,button_2,button_3,button_4;
    private EditText title_editText,descp_editText,selling_price_editText,mobile_editText,alt_mobile_editText;
    private Button sellButton;
    private CheckBox payment_cash,payment_online;
    private CheckBox drop_courier,drop_pickup;
    private RadioGroup product_condition;


    private String CATEGORY_HOLDER,SUB_CATEGORY_HOLDER;

    private String ARRAY_CHOICES[] = {"Camera","Gallery","Remove Image"};
    private AlertDialog alertDialog;

    // Request Codes
    static final int REQUEST_CAMERA = 11;
    static final int REQUEST_GALLERY = 22;
    private ImageButton BUTTON_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_to_sell);

        // back button Management
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // manage the id's
        setID();
        // set up Spinners
        setupSpinners();
        // button Action Manager Manages the actions
        buttonActionManager();

    }

    private void sellAction()
    {
        String title = title_editText.getText().toString().trim();
        String description = descp_editText.getText().toString().trim();
        // RadiioGroup
        String price = selling_price_editText.getText().toString().trim();
        String mobile = mobile_editText.getText().toString().trim();
        String alt_mobile = alt_mobile_editText.getText().toString().trim();

        if(title.equals("") || description.equals("") || price.equals("") || mobile.equals("") ||
                alt_mobile.equals("") || (!payment_cash.isChecked() && !payment_online.isChecked())
                || (!drop_pickup.isChecked() && !drop_courier.isChecked()))
        {
            Toast.makeText(this, ""+"Please Fill All the fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if((!Patterns.PHONE.matcher(mobile).matches() && mobile.length() !=10 ) || (!Patterns.PHONE.matcher(alt_mobile).matches()
                            && alt_mobile.length() != 10))
            {
                Toast.makeText(this, ""+"Enter a valid phone no", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "All Good", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v == button_1)
        {
            BUTTON_ID = button_1;
            alertDialog.show();
        }

        if (v == button_2)
        {
            BUTTON_ID = button_2;
            alertDialog.show();
        }
        if (v == button_3)
        {
            BUTTON_ID = button_3;
            alertDialog.show();
        }
        if(v == button_4)
        {
            BUTTON_ID = button_4;
            alertDialog.show();
        }

        if(v == sellButton)
        {
            sellAction();
        }

    }

    private void setID()
    {
        button_1 =(ImageButton)findViewById(R.id.rts_button1);
        button_2 =(ImageButton)findViewById(R.id.rts_button2);
        button_3 = (ImageButton)findViewById(R.id.rts_button3);
        button_4 = (ImageButton)findViewById(R.id.rts_button4);

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);

        title_editText = (EditText)findViewById(R.id.ready_to_sell_title_editText);
        descp_editText = (EditText)findViewById(R.id.ready_to_sell_descp_editText);
        selling_price_editText = (EditText)findViewById(R.id.ready_to_sell_selling_price_editText);
        mobile_editText = (EditText)findViewById(R.id.ready_to_sell_mobile);
        alt_mobile_editText = (EditText)findViewById(R.id.ready_to_sell_alt_mobile);

        payment_cash = (CheckBox)findViewById(R.id.ready_to_sell_payment_cash_checkbox);
        payment_online = (CheckBox)findViewById(R.id.ready_to_sell_payment_online_checkbox);
        drop_courier = (CheckBox)findViewById(R.id.ready_to_sell_drop_courier_checkbox);
        drop_pickup = (CheckBox)findViewById(R.id.ready_to_sell_drop_pickup_checkbox);

        sellButton = (Button)findViewById(R.id.ready_to_sell_SELL_button);
        sellButton.setOnClickListener(this);
    }
    private void buttonActionManager()
    {

        alertDialog =  new AlertDialog.Builder(this).setTitle("Image Selector")
                .setItems(ARRAY_CHOICES, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which == 0)
                        {
                            cameraAction();
                        }
                        else if(which == 1)
                        {
                            galleryAction();
                        }
                        else if(which == 2)
                        {
                            BUTTON_ID.setImageResource(R.drawable.ic_menu_gallery);
                        }
                    }
                }).create();


    }

    private void cameraAction()
    {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);
    }

    private void galleryAction()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK )
        {
            if (requestCode == REQUEST_CAMERA)
            {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap)extras.get("data");
                BUTTON_ID.setImageBitmap(bitmap);
            }

            if(requestCode == REQUEST_GALLERY && data != null && data.getData()!=null )
            {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                    BUTTON_ID.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
            Toast.makeText(ReadyToSell.this, "Failed to Get Image", Toast.LENGTH_SHORT).show();


    }

    private void setupSpinners()
    {
        category = (Spinner)findViewById(R.id.ready_to_sell_category_spinner);
        sub_category =(Spinner)findViewById(R.id.ready_to_sell_sub_category_spinner);
        category_array = new ArrayList<>();
        sub_category_array = new ArrayList<>();

        fetchArrayData();

        category.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item
                ,category_array));

        sub_category.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item
                ,sub_category_array));

        CATEGORY_HOLDER = category_array.get(0);
        SUB_CATEGORY_HOLDER = sub_category_array.get(0);

    }

    private void fetchArrayData()
    {
        for(int i=0;i<5;i++)
        {
            category_array.add("category "+i);
            sub_category_array.add("sub category "+i);
        }
    }




}
