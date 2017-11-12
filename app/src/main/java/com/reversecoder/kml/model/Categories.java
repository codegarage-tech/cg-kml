package com.reversecoder.kml.model;

/**
 * Created by alam on 6/15/16.
 */
public class Categories {

    private String id="";
    private String title="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public String toString() {
        return "Categories{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
