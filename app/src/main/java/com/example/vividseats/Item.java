package com.example.vividseats;

import android.graphics.Bitmap;

public class Item {
    //    private String image;
    private Bitmap image;
    private String title;
    private String subtitle;
    private String description;
    private String count;

    public Item(Bitmap image, String title, String subtitle, String description, String count) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.count = count;
    }

//    public String getImage() {
//        return image;
//    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }

    public String getCount() {
        return count;
    }
}
