package com.example.hkohli.shoppingapplication;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hkohli on 1/13/2017.
 */
public class FeedRVAdapter extends RecyclerView.Adapter<FeedRVAdapter.ViewHolder> {


    ArrayList<ProductInformation> arrayList;

    public FeedRVAdapter(ArrayList<ProductInformation> arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_cards,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AsyncImageManager asyncImageManager = new AsyncImageManager(arrayList.get(position).image_url,holder.imageView);
        asyncImageManager.execute();
        holder.condition.setText(arrayList.get(position).condition);
        holder.description.setText(arrayList.get(position).details);
        holder.price.setText(arrayList.get(position).price);
        holder.location.setText(arrayList.get(position).location);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView description,price,location,condition;


        public ViewHolder(final View itemView) {
            super(itemView);

            cardView = (CardView)itemView.findViewById(R.id.small_card_cardview);
            imageView = (ImageView)itemView.findViewById(R.id.small_card_imageview);
            description = (TextView)itemView.findViewById(R.id.small_card_product_description);
            price = (TextView)itemView.findViewById(R.id.small_card_price);
            location = (TextView)itemView.findViewById(R.id.small_card_location);
            condition = (TextView)itemView.findViewById(R.id.small_card_condition);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(),ProductDetails.class);
                    intent.putExtra("PRODUCT_ID",arrayList.get(getAdapterPosition()).product_id);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }

}
