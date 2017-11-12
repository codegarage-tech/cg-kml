package com.reversecoder.kml.activity;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.reversecoder.kml.R;
import com.reversecoder.kml.guillotinemenu.ParentActivity;

import is.arontibo.library.ElasticDownloadView;
import is.arontibo.library.ProgressDownloadView;


public class DownloadActivity extends ParentActivity {

    ElasticDownloadView mElasticDownloadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        mElasticDownloadView=(ElasticDownloadView)findViewById(R.id.elastic_download_view);

        ((Button)findViewById(R.id.btn_success)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mElasticDownloadView.startIntro();
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mElasticDownloadView.success();
                    }
                }, 2 * ProgressDownloadView.ANIMATION_DURATION_BASE);
            }
        });

        ((Button)findViewById(R.id.btn_fail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mElasticDownloadView.startIntro();
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mElasticDownloadView.setProgress(45);
                    }
                }, 2 * ProgressDownloadView.ANIMATION_DURATION_BASE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mElasticDownloadView.fail();
                    }
                }, 3 * ProgressDownloadView.ANIMATION_DURATION_BASE);

            }
        });
    }

}
