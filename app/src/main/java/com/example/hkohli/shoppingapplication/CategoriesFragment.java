package com.example.hkohli.shoppingapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {


    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_search_results, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.searchResults_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                            getContext(),2, GridLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.l22);
        arrayList.add(R.drawable.l22);
        arrayList.add(R.drawable.l22);
        arrayList.add(R.drawable.l22);
        arrayList.add(R.drawable.l1);

        recyclerView.setAdapter(new ImageAdapterCategoriesPage(arrayList,getContext()));


        return view;
    }

}
