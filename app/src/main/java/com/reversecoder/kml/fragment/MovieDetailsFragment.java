package com.reversecoder.kml.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reversecoder.kml.R;
import com.reversecoder.kml.activity.HomeActivity;
import com.reversecoder.kml.model.Movies;

public class MovieDetailsFragment extends Fragment {

    private View parentView;
    TextView tvMdDirector, tvMdStars, tvMdPlotSummary, tvMdSynopsis;


    public static MovieDetailsFragment newInstance() {
        return new MovieDetailsFragment();
    }

    public MovieDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Movies mMovie=((HomeActivity)getActivity()).getSelectedMovie();

        tvMdDirector=(TextView)parentView.findViewById(R.id.md_director);
        tvMdStars=(TextView)parentView.findViewById(R.id.md_stars);
        tvMdPlotSummary=(TextView)parentView.findViewById(R.id.md_plot_summary);
        tvMdSynopsis=(TextView)parentView.findViewById(R.id.md_synopsis);

        tvMdDirector.setText(mMovie.getDirector());
        tvMdStars.setText(mMovie.getStars());
        tvMdPlotSummary.setText(mMovie.getPlotSummary());
        tvMdSynopsis.setText(mMovie.getSynopsis());

        return parentView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
