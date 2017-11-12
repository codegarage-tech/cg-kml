package com.reversecoder.kml.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.reversecoder.kml.model.AdMob;
import com.reversecoder.kml.model.Movies;
import com.reversecoder.kml.viewholder.AdMobViewHolder;
import com.reversecoder.kml.viewholder.MovieViewHolder;

import java.security.InvalidParameterException;

public class MovieWithAdMobAdapter extends  RecyclerArrayAdapter<Object> {
    public static final int TYPE_INVALID = 0;
    public static final int TYPE_ADMOB = 1;
    public static final int TYPE_MOVIE = 2;

    public MovieWithAdMobAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewType(int position) {
        if (getItem(position) instanceof AdMob) {
            return TYPE_ADMOB;
        } else if (getItem(position) instanceof Movies) {
            return TYPE_MOVIE;
        }
        return TYPE_INVALID;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_MOVIE:
                return new MovieViewHolder(parent);
            case TYPE_ADMOB:
                return new AdMobViewHolder(parent);
            default:
                throw new InvalidParameterException();
        }
    }
}