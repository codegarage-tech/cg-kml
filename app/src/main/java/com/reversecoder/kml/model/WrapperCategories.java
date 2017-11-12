package com.reversecoder.kml.model;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by alam on 6/15/16.
 */
public class WrapperCategories {

    private String successCode="";
    private String successMessage="";
    private ArrayList<Categories> categories=new ArrayList<Categories>();

    public String getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(String successCode) {
        this.successCode = successCode;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public ArrayList<Categories> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categories> categories) {
        this.categories = categories;
    }

    public static WrapperCategories getResponseData(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, WrapperCategories.class);
    }
}
