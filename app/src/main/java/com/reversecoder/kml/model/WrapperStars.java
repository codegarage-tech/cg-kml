package com.reversecoder.kml.model;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by alam on 6/15/16.
 */
public class WrapperStars {

    private String successCode = "";
    private String successMessage = "";
    private ArrayList<Stars> stars = new ArrayList<Stars>();

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

    public ArrayList<Stars> getStars() {
        return stars;
    }

    public void setStars(ArrayList<Stars> stars) {
        this.stars = stars;
    }

    public static WrapperStars getResponseData(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, WrapperStars.class);
    }
}
