package com.example.hkohli.shoppingapplication;

/**
 * Created by Hkohli on 9/3/2016.
 */
public class MyOrdersInformation {

    int image;
    String price,product_detail,email,mobile,message,location;

    public MyOrdersInformation(int image,String product_detail,String price,String location,
                               String email,String mobile,String message)
    {
        this.image = image;
        this.product_detail = product_detail;
        this.price = price;
        this.location = location;
        this.email = email;
        this.mobile = mobile;
        this.message = message;

    }
}
