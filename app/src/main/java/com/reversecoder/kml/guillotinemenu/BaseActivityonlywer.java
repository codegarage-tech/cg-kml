package com.reversecoder.kml.guillotinemenu;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.reversecoder.kml.R;

public class BaseActivityonlywer extends ParentActivity {

	public LayoutInflater inflater;
	public FrameLayout baseLayout;
	public LinearLayout inflateLayout;

	// menu
	View gMenuLayout;
	public static final long RIPPLE_DURATION = 250;
	GuillotineAnimation gAnimation;
	CustomFontTextView txtActivityTitle, txtMenuTitle;
	ImageView materialMenuClosingView, materialMenuOpeningView;
	private int menuState = 0;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(getParentContext());
		setContentView(R.layout.activity_base);
		baseLayout = (FrameLayout) findViewById(R.id.root);
		inflateLayout = (LinearLayout) findViewById(R.id.base_layout);

		// set up toolbar
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
			getSupportActionBar().setTitle(null);
		}

		// set up menu view
		gMenuLayout = LayoutInflater.from(this).inflate(R.layout.base_menu, null);
		baseLayout.addView(gMenuLayout);

		// initialize view
		txtActivityTitle = (CustomFontTextView) baseLayout.findViewById(R.id.txt_title);
		txtActivityTitle.setText("Activity");
		txtMenuTitle = (CustomFontTextView) gMenuLayout.findViewById(R.id.txt_title);
		txtMenuTitle.setText("Menu");
		materialMenuOpeningView = (ImageView) baseLayout.findViewById(R.id.content);
		materialMenuClosingView = (ImageView) gMenuLayout.findViewById(R.id.content);

		gAnimation = new GuillotineAnimation.GuillotineBuilder(gMenuLayout, (View) materialMenuClosingView,
				(View) materialMenuOpeningView).setStartDelay(RIPPLE_DURATION).setActionBarViewForAnimation(toolbar)
						.setClosedOnStart(true).build();

		materialMenuOpeningView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gAnimation.open();
			}
		});

		materialMenuClosingView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gAnimation.close();
			}
		});

		// setUpMenuView(R.layout.base_menu);
	}
	
	public void onBackPressed(){
		if(gAnimation.isOpened()){
			gAnimation.close();
		}else{
			super.onBackPressed();
		}
	}

	// public void setUpMenuView(int layout) {
	// gMenuLayout = LayoutInflater.from(this).inflate(layout, null);
	// baseLayout.addView(gMenuLayout);
	// gAnimation = new GuillotineAnimation.GuillotineBuilder(this, gMenuLayout,
	// ()gMenuLayout.findViewById(R.id.content),
	// materialMenu).setStartDelay(RIPPLE_DURATION)
	// .setActionBarViewForAnimation(toolbar).setClosedOnStart(true).build();
	// gAnimation.setOnClickListner(new GuillotineListener() {
	//
	// @Override
	// public void onGuillotineOpened() {
	// // TODO Auto-generated method stub
	// // txtMenuTitle.setText("Menu");
	//
	// }
	//
	// @Override
	// public void onGuillotineClosed() {
	// // TODO Auto-generated method stub
	// txtActivityTitle.setText("Activity");
	// }
	// });
	// }

	// public void setTitle(String title) {
	// mTitle.setText(title);
	// }

}
