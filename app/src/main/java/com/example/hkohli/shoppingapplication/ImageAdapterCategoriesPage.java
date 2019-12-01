package com.example.hkohli.shoppingapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Hkohli on 9/29/2016.
 */
public class ImageAdapterCategoriesPage extends RecyclerView.Adapter<ImageAdapterCategoriesPage.ImageAdapterViewHolder>{

    ArrayList<Integer> arrayList;
    Context context;
    ImageView imageView;

    public ImageAdapterCategoriesPage(ArrayList<Integer> arrayList,Context context)
    {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ImageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        imageView = new ImageView(context);

        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 400
        );
        layoutParams.setMargins(5,5,5,5);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        ImageAdapterViewHolder viewHolder = new ImageAdapterViewHolder(imageView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ImageAdapterViewHolder holder, int position) {

        holder.imageView.setImageResource(arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ImageAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ImageAdapterViewHolder(ImageView itemView) {
            super(itemView);
            imageView = itemView;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), ""+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
