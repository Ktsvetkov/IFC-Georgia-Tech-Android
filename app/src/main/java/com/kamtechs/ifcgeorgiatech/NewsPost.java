package com.kamtechs.ifcgeorgiatech;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Calendar;

/**
 * Created by Kamenator on 10/27/15.
 */
public class NewsPost {

    //int timeSince1970 = 0;
    String message = "";
    String imageURL = "";
    Bitmap picture = null;
    Calendar calendar = null;
    boolean hasPicture;

    public NewsPost (Calendar calendar, String message, String imageURL, boolean hasImage) {
        this(calendar, message, imageURL, hasImage, null);
    }

    public NewsPost (Calendar calendar, String message, String imageURL, boolean hasPicture, Bitmap picture) {
        this.calendar = calendar;
        this.message = message;
        this.imageURL = imageURL;
        this.picture = picture;
        this.hasPicture = hasPicture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

}

