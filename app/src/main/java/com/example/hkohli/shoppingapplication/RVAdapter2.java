package com.example.hkohli.shoppingapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Hkohli on 9/3/2016.
 */
public class RVAdapter2 extends RecyclerView.Adapter<RVAdapter2.ViewHolder> {

    private ArrayList<MyOrdersInformation> arrayList;

    public RVAdapter2(ArrayList<MyOrdersInformation> arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_myorders,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.imageView.setImageResource(arrayList.get(position).image);
        holder.product_details.setText(arrayList.get(position).product_detail);
        holder.price.setText(arrayList.get(position).price);
        holder.email.setText(arrayList.get(position).email);
        holder.mobile.setText(arrayList.get(position).mobile);
        holder.message.setText(arrayList.get(position).message);
        holder.location.setText(arrayList.get(position).location);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    // View generator class

    class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        TextView price,product_details,email,mobile,message,location;
        View refferenceView;

        public ViewHolder(View itemView) {
            super(itemView);

            refferenceView = itemView;
            imageView = (ImageView)itemView.findViewById(R.id.layout_myorders_image);
            price = (TextView)itemView.findViewById(R.id.layout_myorders_product_price);
            product_details = (TextView)itemView.findViewById(R.id.layout_myorders_product_details);
            email = (TextView)itemView.findViewById(R.id.layout_myorders_email);
            mobile =(TextView)itemView.findViewById(R.id.layout_myorders_mobile);
            message =(TextView)itemView.findViewById(R.id.layout_myorders_message);
            location = (TextView)itemView.findViewById(R.id.layout_myorders_location);

        }
    }

}
