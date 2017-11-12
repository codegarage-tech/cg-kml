package com.reversecoder.kml.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jude.easyrecyclerview.AdvancedRecyclerViewWithWaveSwipeRefreshLayout;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.jude.easyrecyclerview.util.Utils;
import com.jude.easyrecyclerview.waveswiperefreshlayout.WaveSwipeRefreshLayout;
import com.reversecoder.kml.R;
import com.reversecoder.kml.adapter.MovieWithAdMobAdapter;
import com.reversecoder.kml.guillotinemenu.ParentActivity;
import com.reversecoder.kml.model.WrapperMovies;
import com.reversecoder.kml.util.AndroidAssetManager;

public class AdvancedRecyclerViewActivity extends ParentActivity{
    private AdvancedRecyclerViewWithWaveSwipeRefreshLayout recyclerView;
    //    private FloatingActionButton top;
    private MovieWithAdMobAdapter adapter;
    private Handler handler = new Handler();

    private int page = 0;
    private boolean hasNetWork = true;
    private long waitTime = 5000;

    RecyclerArrayAdapter.OnLoadMoreListener onLoadMoreListener=new RecyclerArrayAdapter.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            Log.i("EasyRecyclerView", "onLoadMore");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WrapperMovies responseMovies = WrapperMovies.getResponseData(AndroidAssetManager.readTextFileFromRaw(AdvancedRecyclerViewActivity.this, R.raw.kml_movie_list));

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

    WaveSwipeRefreshLayout.OnRefreshListener onRefreshListener=new WaveSwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 0;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.clear();
                    WrapperMovies responseMovies = WrapperMovies.getResponseData(AndroidAssetManager.readTextFileFromRaw(AdvancedRecyclerViewActivity.this, R.raw.kml_movie_list));

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_recyclerview);

//        top = (FloatingActionButton) findViewById(R.id.top);
        recyclerView = (AdvancedRecyclerViewWithWaveSwipeRefreshLayout) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, Utils.dip2px(this, 0.5f), Utils.dip2px(this, 72), 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setAdapterWithProgress(adapter = new MovieWithAdMobAdapter(this));
        adapter.setMore(R.layout.view_more, onLoadMoreListener);
        adapter.setNoMore(R.layout.view_nomore);
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemClick(int position) {
//                adapter.remove(position);
                return true;
            }
        });
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });

//        top.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recyclerView.scrollToPosition(0);
//            }
//        });
        recyclerView.setRefreshListener(onRefreshListener);
        onRefresh();
    }

//    @Override
//    public void onLoadMore() {
//        Log.i("EasyRecyclerView", "onLoadMore");
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                WrapperMovies responseMovies = WrapperMovies.getResponseData(AndroidAssetManager.readTextFileFromRaw(AdvancedRecyclerViewActivity.this, R.raw.kml_movie_list));
//
//                //刷新
//                if (!hasNetWork) {
//                    adapter.pauseMore();
//                    return;
//                }
////                adapter.addAll(DataProvider.getPersonList(page));
//                adapter.addAll(responseMovies.getMovieListWithAds(0));
//                page++;
//            }
//        }, waitTime);
//    }

    public void onRefresh() {
        page = 0;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                WrapperMovies responseMovies = WrapperMovies.getResponseData(AndroidAssetManager.readTextFileFromRaw(AdvancedRecyclerViewActivity.this, R.raw.kml_movie_list));

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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        MenuItem item = menu.findItem(R.id.checkbox);
//        CheckBox box = (CheckBox) item.getActionView();
//        box.setChecked(true);
//        box.setText("网络");
//        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                hasNetWork = isChecked;
//            }
//        });
//        return true;
//    }


}
