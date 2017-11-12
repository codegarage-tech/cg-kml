package com.reversecoder.kml.model;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by alam on 6/15/16.
 */
public class WrapperMovies {

    private String successCode="";
    private String successMessage="";
    private ArrayList<Movies> movieList=new ArrayList<Movies>();

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

    public ArrayList<Movies> getMovieList() {
        return movieList;
    }

//    public ArrayList<Movies> getMovieListWithAd() {
//        ArrayList<Movies> tempMovies= new ArrayList<Movies>();
//        if(movieList.size()>0){
//            for(int i=1;i<movieList.size();i++){
//                if(i%5==0 ) {
//                    tempMovies.add(null);
//                }
//                tempMovies.add(movieList.get(i));
//            }
//        }
//        return tempMovies;
//    }

    public ArrayList<Object> getMovieListWithAds(int page){
        ArrayList<Object> arrAll = new ArrayList<>();
        ArrayList<AdMob> arrAd = getAdList();
        int index = 0;
        for (Movies movie : getMovieList()) {
            arrAll.add(movie);
            if (Math.random()<0.2){
                arrAll.add(arrAd.get(index%arrAd.size()));
                index++;
            }
        }

        return arrAll;
    }

    public static ArrayList<AdMob> getAdList(){
        ArrayList<AdMob> arr = new ArrayList<AdMob>();
        arr.add(new AdMob("Add 1","http://i2.hdslb.com/u_user/e97a1632329cac1fa6ab3362e7233a08.jpg"));
        arr.add(new AdMob("Add 2","http://i1.hdslb.com/u_user/80fcc32d0b5d3565377847bd9dd06dc3.jpg"));
        arr.add(new AdMob("Add 3","http://i2.hdslb.com/u_user/f19f0e44328a4190a48acf503c737c50.png"));
        arr.add(new AdMob("Add 4","http://i1.hdslb.com/u_user/7ee1aeadc8257f43fa6b806717c9c398.png"));
        return arr;
    }

    public void setMovieList(ArrayList<Movies> movieList) {
        this.movieList = movieList;
    }

    public static WrapperMovies getResponseData(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, WrapperMovies.class);
    }
}
