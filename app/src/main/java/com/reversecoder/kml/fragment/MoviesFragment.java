package com.reversecoder.kml.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.easyrecyclerview.util.Utils;
import com.reversecoder.kml.R;
import com.reversecoder.kml.adapter.MovieWithAdMobAdapter;
import com.reversecoder.kml.model.WrapperMovies;
import com.reversecoder.kml.util.AndroidAssetManager;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {

    private EasyRecyclerView mRecyclerView;
    private List<String> items;
    private boolean status_progress = false;
//    private WrapperMovies responseMovies;
//    MoviesFragmentAdvancedAdapter mAdapter;

    private final String SAVED_ITEMS = "saved_items";

    //easy recyclerview
    private MovieWithAdMobAdapter adapter;
    private Handler handler = new Handler();

    private int page = 0;
    private boolean hasNetWork = true;
    private long waitTime = 5000;
    DividerDecoration itemDecoration;

    RecyclerArrayAdapter.OnLoadMoreListener onLoadMoreListener = new RecyclerArrayAdapter.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            Log.i("EasyRecyclerView", "onLoadMore");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WrapperMovies responseMovies = WrapperMovies.getResponseData(AndroidAssetManager.readTextFileFromRaw(getActivity(), R.raw.kml_movie_list));

                    //刷新
                    if (!hasNetWork) {
                        adapter.pauseMore();
                        return;
                    }
//                adapter.addAll(DataProvider.getPersonList(page));
                    adapter.addAll(responseMovies.getMovieListWithAds(0));
                    page++;
                }
            }, waitTime);
        }
    };

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 0;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.clear();
                    WrapperMovies responseMovies = WrapperMovies.getResponseData(AndroidAssetManager.readTextFileFromRaw(getActivity(), R.raw.kml_movie_list));

                    if (!hasNetWork) {
                        adapter.pauseMore();
                        return;
                    }
                    adapter.addAll(responseMovies.getMovieListWithAds(0));
                    page = 1;
                }
            }, waitTime);
        }
    };

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    public MoviesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstaceState) {
        mRecyclerView = (EasyRecyclerView) inflater
                .inflate(R.layout.fragment_movies, parent, false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setProgressView(R.layout.view_progress);

        itemDecoration = new DividerDecoration(Color.GRAY, Utils.dip2px(getActivity(), 0.5f), Utils.dip2px(getActivity(), 72), 0);
        itemDecoration.setDrawLastItem(false);
        mRecyclerView.addItemDecoration(itemDecoration);

        adapter = new MovieWithAdMobAdapter(getActivity());
        mRecyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.view_more, onLoadMoreListener);
        adapter.setNoMore(R.layout.view_nomore);

//        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
//            @Override
//            public boolean onItemClick(int position) {
////                adapter.remove(position);
//                return true;
//            }
//        });

//        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                if(adapter.getViewType(position)==MovieWithAdMobAdapter.TYPE_MOVIE){
//                    ((HomeActivity) getContext()).openDraggableView((Movies) adapter.getItem(position));
//                }
//            }
//        });
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });

        mRecyclerView.setRefreshListener(onRefreshListener);
        onRefresh();

        return mRecyclerView;
    }

    public void onRefresh() {
        page = 0;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                WrapperMovies responseMovies = WrapperMovies.getResponseData(AndroidAssetManager.readTextFileFromRaw(getActivity(), R.raw.kml_movie_list));

                //刷新
                if (!hasNetWork) {
                    adapter.pauseMore();
                    return;
                }
                adapter.addAll(responseMovies.getMovieListWithAds(0));
                page = 1;
            }
        }, waitTime);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            items = savedInstanceState.getStringArrayList(SAVED_ITEMS);
//        }
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
                if (c % 5 == 2 || c % 3 == 1 || c % 7 == 4) {
                    items.add(String.valueOf(c));
                }
            }
        }

        return items;
    }

    /**
    * swipe fixes
    * */

    public void setSwipeToRefreshEnabled(boolean enabled) {
        mRecyclerView.setSwipeEnable(enabled);
    }
}