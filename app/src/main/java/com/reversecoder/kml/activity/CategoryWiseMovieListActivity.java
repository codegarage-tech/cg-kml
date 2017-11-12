package com.reversecoder.kml.activity;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.reversecoder.kml.R;
import com.reversecoder.kml.adapter.CategoryWiseMovieListAdapter;
import com.reversecoder.kml.adapter.MoviesFragmentAdapter;
import com.reversecoder.kml.guillotinemenu.ParentActivity;
import com.reversecoder.kml.model.WrapperMovies;
import com.reversecoder.kml.util.AndroidAssetManager;


public class CategoryWiseMovieListActivity extends ParentActivity {

    CategoryWiseMovieListAdapter categoryWiseMovieListAdapter;
    Context mContext;
    ListView listCategoryWiseMovie;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category_wise_movie_list);
        setContentView(R.layout.fragment_movies);

        final WrapperMovies responseMovies = WrapperMovies.getResponseData(AndroidAssetManager.readTextFileFromRaw(CategoryWiseMovieListActivity.this, R.raw.kml_movie_list));
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CategoryWiseMovieListActivity.this));
        MoviesFragmentAdapter mAdapter = new MoviesFragmentAdapter(CategoryWiseMovieListActivity.this, responseMovies.getMovieList());
        mRecyclerView.setAdapter(mAdapter);

//        initCategoryWiseMovieListUI();
    }

//    private void initCategoryWiseMovieListUI() {
//        mContext=CategoryWiseMovieListActivity.this;
//        final WrapperMovies responseMovies = WrapperMovies.getResponseData(AndroidAssetManager.readTextFileFromRaw(mContext, R.raw.kml_movie_list));
//        categoryWiseMovieListAdapter = new CategoryWiseMovieListAdapter(CategoryWiseMovieListActivity.this);
//        listCategoryWiseMovie = (ListView) findViewById(R.id.listCategoryWiseMovie);
//        listCategoryWiseMovie.setAdapter(categoryWiseMovieListAdapter);
//        categoryWiseMovieListAdapter.setData(responseMovies.getMovieList());
//    }

}
