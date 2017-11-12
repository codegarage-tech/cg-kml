package com.reversecoder.kml.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.reversecoder.library.customview.animatedtextview.AnimatedTextView;
import com.reversecoder.library.customview.animatedtextview.AnimatedTextViewType;
import com.reversecoder.kml.R;
import com.reversecoder.kml.guillotinemenu.ParentActivity;


public class SplashActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startSplashScreenTimer();
        AnimatedTextView animatedTextView = (AnimatedTextView) findViewById(R.id.text);
        animatedTextView.setAnimateType(AnimatedTextViewType.ANVIL);
        animatedTextView.animateText("Korean Movie Lovers");
    }

    private void startSplashScreenTimer() {
        CountDownTimer mSplashTimer = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                goHomeScreen();

            }
        };
        mSplashTimer.start();
    }

    private void goHomeScreen() {
        Intent intent = new Intent(getParentContext(), HomeActivity.class);;
        startActivity(intent);
        finish();
    }
}
