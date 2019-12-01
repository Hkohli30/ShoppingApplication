package com.example.hkohli.shoppingapplication;

import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Hkohli on 7/29/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.InfoViewHolder> {

    ArrayList<ProductInformation> arrayList;
    private String TYPE;
    private String SHOP_TYPE;
    private PopupMenu popUpMenu;

    // Constructor
    public RVAdapter(ArrayList<ProductInformation> arrayList) {
        this.arrayList = arrayList;
    }

    public void typeSetter(String TYPE)
    {
        this.TYPE = TYPE;
    }

    public void shopTYPE(String SHOP_TYPE)
    {
        this.SHOP_TYPE = SHOP_TYPE;
    }


    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        InfoViewHolder infoViewHolder = new InfoViewHolder(view);
        return infoViewHolder;
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        holder.desc.setText(arrayList.get(position).details);
        holder.price.setText(arrayList.get(position).price);
        holder.location.setText(arrayList.get(position).location);
        holder.condition.setText(arrayList.get(position).condition);
        AsyncImageManager asyncImageManager = new AsyncImageManager(arrayList.get(position).image_url,holder.imageView);
        asyncImageManager.execute();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView imageView;
        TextView desc,price,location,condition;
        ImageButton optionsButton;

        public InfoViewHolder(View the_layout) {
            super(the_layout);
//            layout.setOnClickListener(this);
            cardView = (CardView) the_layout.findViewById(R.id.card_layout_cardView);
            imageView = (ImageView) the_layout.findViewById(R.id.card_layout_imageview);
            desc = (TextView) the_layout.findViewById(R.id.card_layout_product_description);
            price = (TextView) the_layout.findViewById(R.id.card_layout_price);
            location = (TextView) the_layout.findViewById(R.id.card_layout_location);
            condition = (TextView)the_layout.findViewById(R.id.card_layout_condition);
            optionsButton = (ImageButton)the_layout.findViewById(R.id.card_layout_options_button);

            cardView.setOnClickListener(this);
            optionsButton.setOnClickListener(this);
            if(TYPE.equals("result") || !SHOP_TYPE.equals("MY"))
                optionsButton.setVisibility(View.INVISIBLE);

        }




        @Override
        public void onClick(View v) {

            if (v == cardView) {

                if(TYPE.equalsIgnoreCase("result"))
                {
                    Toast.makeText(v.getContext(),""+getAdapterPosition()
                            +""+TYPE+" 1", Toast.LENGTH_SHORT).show();

                }
                else if(TYPE.equalsIgnoreCase("shop"))
                {
                    Toast.makeText(v.getContext(),""+getAdapterPosition()
                            + ""+TYPE+" 2", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(),ProductDetails.class);
                    intent.putExtra("PRODUCT_ID",arrayList.get(getAdapterPosition()).product_id);
                    v.getContext().startActivity(intent);
                }
            }

            if (v == optionsButton)
            {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),optionsButton);
                popupMenu.inflate(R.menu.search_results_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        switch (id)
                        {
                            case R.id.search_results_search :
                                Toast.makeText(optionsButton.getContext(), ""
                                        + "Search "+ getAdapterPosition() , Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.search_results_filter :

                                Toast.makeText(optionsButton.getContext(), ""
                                        + "Filter "+ getAdapterPosition() , Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }

        }
    }

}