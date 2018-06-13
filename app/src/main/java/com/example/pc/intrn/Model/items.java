package com.example.pc.intrn.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class items {

    Drawable image;
    String title;
    String provider;
    String price;

    public items(Drawable image, String title, String provider, String price) {
        this.image = image;
        this.title = title;
        this.provider = provider;
        this.price = price;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
