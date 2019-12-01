package com.example.hkohli.shoppingapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResults extends AppCompatActivity implements SenderInterface {

    private RecyclerView recyclerView;
    private ArrayList<ProductInformation> arrayList;
    private RVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intent intent = getIntent();
        //String SEARCH_KEYWORD = intent.getStringExtra("key");
        //String TEMP = "shoes";
        String results_url = getResources().getString(R.string.SearchResultsURL);


        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction()))
        {
            String data = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, ""+data, Toast.LENGTH_SHORT).show();
            SenderManager senderManager = new SenderManager(this,results_url);
            senderManager.DELEGATE_RESPONSE = this;
            senderManager.execute("search_key",data);
        }

        setUpRecyclerView();

    }

    private void setUpRecyclerView()
    {
        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.searchResults_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        PreCatchingLayoutManager layoutManager = new PreCatchingLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        layoutManager.setExtraLayoutSpace(layoutManager.heightSetter(this));
        rvAdapter = new RVAdapter(arrayList);
        rvAdapter.typeSetter("result");

        recyclerView.setItemViewCacheSize(12);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rvAdapter);
    }

    private void setUpArrayList(String output) throws JSONException {
        JSONObject jsonObject1 = new JSONObject(output);
        JSONArray jsonArray = jsonObject1.getJSONArray("results");

        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String image_url = jsonObject.getString("image");
            String details = jsonObject.getString("title");
            String price = "Rs."+jsonObject.getString("sp");
            String location = jsonObject.getString("state");
            String condition = jsonObject.getString("condition");
            String product_id = jsonObject.getString("id");
            arrayList.add(new ProductInformation(image_url,details,price,"jalandhar",condition,product_id));
        }
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_results_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.search_results_search)
        {
            startActivity(new Intent(this,SearchActivity.class));
            return true;
        }

        if(id == R.id.search_results_filter)
        {
            Toast.makeText(SearchResults.this, "filter", Toast.LENGTH_SHORT).show();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void senderInterfaceResult(String output) {
        if(output != null)
        {
            try {
                setUpArrayList(output.trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
