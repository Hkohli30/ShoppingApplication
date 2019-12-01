package com.example.hkohli.shoppingapplication;

/**
 * Created by Hkohli on 7/29/2016.
 */
public class ProductInformation {

    String details,price,location,condition,product_id;
    int image;
    String image_url;

    public ProductInformation(int image,String details,String price,String location)
    {
        this.image = image;
        this.details = details;
        this.location = location;
        this.price = price;
    }

    public ProductInformation(String image_url,String details,String price,String location,String condition,
                              String product_id)
    {
        this.image_url = image_url;
        this.details = details;
        this.price = price;
        this.location = location;
        this.condition = condition;
        this.product_id = product_id;
    }



}
