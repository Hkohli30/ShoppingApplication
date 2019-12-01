package com.example.hkohli.shoppingapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements JSONInterface{


    public FeedFragment() {
        // Required empty public constructor
    }

    private String URL;
    private ImageView banner,banner2;
    private String JSONDATA;
    private View view;
    private RecyclerView recyclerview_top,recyclerView_recent;
    private ArrayList<ProductInformation> top_prod_ArrayList;
    private ArrayList<ProductInformation> recent_prod_ArrayList;
    private FeedRVAdapter adapter_top_products,adapter_recent_products;
    private boolean FLAG = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_feed, container, false);

        // JSON GETTER AND URL GETTER
        URL = getURL();
        if(FLAG)
        {
            JSONGetterClass jsonGetterClass = new JSONGetterClass(getContext());
            jsonGetterClass.delegate = this;
            jsonGetterClass.execute(URL);
        }

        // GETTING THE ID'S
        banner = (ImageView)view.findViewById(R.id.fragment_feed_banner);
        banner2 = (ImageView)view.findViewById(R.id.fragment_feed_banner2);
        banner.getParent().requestChildFocus(banner,banner);

        initilizeRecyclerView(view);
        setupRecyclerViews(recyclerview_top,top_prod_ArrayList,adapter_top_products);
        setupRecyclerViews(recyclerView_recent,recent_prod_ArrayList,adapter_recent_products);


        this.view = view;
        return view;
    }

    private String getURL()
    {
        return getResources().getString(R.string.HomePageURL);
    }

    private void initilizeRecyclerView(View view)
    {
        recyclerview_top = (RecyclerView)view.findViewById(R.id.fragment_feed_recyclerview);
        recyclerView_recent = (RecyclerView)view.findViewById(R.id.fragment_feed_recyclerview2);
        top_prod_ArrayList = new ArrayList<>();
        recent_prod_ArrayList = new ArrayList<>();
        adapter_top_products = new FeedRVAdapter(top_prod_ArrayList);
        adapter_recent_products = new FeedRVAdapter(recent_prod_ArrayList);

    }

    private void setupRecyclerViews(RecyclerView recyclerView, ArrayList<ProductInformation> arrayList
                                    ,FeedRVAdapter adapter)
    {
        PreCatchingLayoutManager preCatchingLayoutManager = new PreCatchingLayoutManager(
                getContext(),1,GridLayoutManager.HORIZONTAL,false);

        preCatchingLayoutManager.setExtraLayoutSpace(preCatchingLayoutManager.witdhSetter(getActivity()));
        recyclerView.setLayoutManager(preCatchingLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void jsonProcessFinished(String output) throws JSONException {
        if(output != null)
        {
            JSONDATA = output.trim();
            JSONManager();
        }
    }

    public void JSONManager()
    {
        try {
            JSONObject jsonObject = new JSONObject(JSONDATA).getJSONObject("results");
            JSONArray jsonArray = jsonObject.getJSONArray("banner");
            JSONArray jsonArray_top_products = jsonObject.getJSONArray("top_prod");
            JSONArray jsonArray_recent_products = jsonObject.getJSONArray("recent_prod");

            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

            AsyncImageManager asyncImageManager1 = new AsyncImageManager(jsonObject1.getString("banner1")
                    ,banner);
            asyncImageManager1.execute();
            AsyncImageManager asyncImageManager = new AsyncImageManager(jsonObject1.getString("banner2")
                                            ,banner2);
            asyncImageManager.execute();

            setupRecyclerData(top_prod_ArrayList,jsonArray_top_products,adapter_top_products);
            setupRecyclerData(recent_prod_ArrayList,jsonArray_recent_products,adapter_recent_products);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupRecyclerData(ArrayList<ProductInformation> arrayList,
                                   JSONArray jsonArray,FeedRVAdapter adapter) throws JSONException {
        if(!arrayList.isEmpty())
            arrayList.clear();
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
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(FLAG)
        {
            FLAG = false;
        }
    }
}
