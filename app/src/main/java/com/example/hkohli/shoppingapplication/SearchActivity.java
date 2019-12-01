package com.example.hkohli.shoppingapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private  boolean FLAG = false;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent searchIntent = getIntent();

        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction()))
        {
            String data = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(SearchActivity.this, ""+data, Toast.LENGTH_SHORT).show();
            FLAG = true;

        }


        setUpListView();


        ImageButton imagebutton = (ImageButton) findViewById(R.id.search_actvity_clear_image);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearList();
            }
        });


    }

    private void setUpListView()
    {

        arrayList = new ArrayList<>();
        listView = (ListView)findViewById(R.id.search_actvity_listview);

        for(int i =0;i<5;i++)
        {
            arrayList.add("abc "+(i+1));
        }

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,
                arrayList);
        listView.setAdapter(adapter);

    }


    private void clearList()
    {
        arrayList.clear();
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.action_search_menu).getActionView();
        SearchManager searchManager = (SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        if(!FLAG) {
            menu.findItem(R.id.action_search_menu).expandActionView();
        }
        return true;
    }


}
