package com.reversecoder.kml.model;

public class AdMob {
    private String adName;
    private String adUrl;

    public AdMob(String image, String url) {
        this.adName = image;
        this.adUrl = url;
    }


    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }
}
