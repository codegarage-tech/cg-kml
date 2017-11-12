package com.reversecoder.kml.guillotinemenu;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class CustomViewApplication extends Application {

	private static Context mContext;
	private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
	public static Typeface canaroExtraBold;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		initTypeface();

		/*
		* Initialized facebook sdk
		* */
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
	}

	private void initTypeface() {
		canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);
	}

	public static Context getGlobalContext() {
		return mContext;
	}
}
