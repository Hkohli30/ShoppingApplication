package com.example.hkohli.shoppingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MyOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MyOrdersInformation> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView)findViewById(R.id.myorder_recyclerview);
        initilizeList();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RVAdapter2 rvAdapter2 = new RVAdapter2(arrayList);
        recyclerView.setAdapter(rvAdapter2);


    }

    private void initilizeList()
    {
        arrayList = new ArrayList<>();

        for(int i=0;i<5;i++)
        {
            arrayList.add(new MyOrdersInformation(R.drawable.download,"llllllllllllllllllllllllll",
                    "Rs.5000","Jalandhar","abc@yahoo.in","99882255889","lllllllllllllllllllllllllllllll"));
        }

    }
}
