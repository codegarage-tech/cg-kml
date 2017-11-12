package com.reversecoder.kml.model;


import android.graphics.Bitmap;

public class SliderModel {

    private String mTitle = "";
    private String mImageURL = "";
    private Bitmap mImageBitmap = null;

    public SliderModel() {
    }

    public SliderModel(String mTitle, Bitmap mImageBitmap, String mImageURL) {
        this.mTitle = mTitle;
        this.mImageBitmap = mImageBitmap;
        this.mImageURL = mImageURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }

    public void setImageBitmap(Bitmap mImageBitmap) {
        this.mImageBitmap = mImageBitmap;
    }
}
