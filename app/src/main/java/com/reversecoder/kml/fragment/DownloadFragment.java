package com.reversecoder.kml.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.reversecoder.library.customview.utils.AppUtils;
import com.reversecoder.kml.R;

public class DownloadFragment extends Fragment {

    private View parentView;

    public static DownloadFragment newInstance() {
        return new DownloadFragment();
    }

    public DownloadFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_download, container, false);

        ((Button)parentView.findViewById(R.id.download)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.launchActivityFromAnotherAppOrDownloadApp(getActivity(),"com.bjit.bdfoodnavi","com.reversecoder.advancedplayer.activity.VideoPlayActivity");
            }
        });

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
