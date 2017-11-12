package com.reversecoder.kml.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reversecoder.kml.R;
import com.reversecoder.kml.adapter.StarsFragmentAdapter;
import com.reversecoder.kml.model.WrapperStars;
import com.reversecoder.kml.util.AndroidAssetManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay Rambhia on 11/06/15.
 */
public class StarsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<String> items;

    private final String SAVED_ITEMS = "saved_items";

    public static StarsFragment newInstance() {
        return new StarsFragment();
    }

    public StarsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstaceState) {
        mRecyclerView = (RecyclerView) inflater
                .inflate(R.layout.fragment_subtitle, parent, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            items = savedInstanceState.getStringArrayList(SAVED_ITEMS);
//        }

        final WrapperStars responseStars = WrapperStars.getResponseData(AndroidAssetManager.readTextFileFromRaw(getActivity(), R.raw.kml_movie_stars_categories));
        StarsFragmentAdapter mAdapter = new StarsFragmentAdapter(getActivity(), responseStars.getStars());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        if (items != null) {
//            outState.putStringArrayList(SAVED_ITEMS, new ArrayList<String>(items));
//        }
    }

    private List<String> getItems() {

        if (items == null) {
            items = new ArrayList<String>();

            for (char c = 'A'; c <= 'Z'; c++) {
                items.add(String.valueOf(c));
            }
        }

        return items;
    }
}
