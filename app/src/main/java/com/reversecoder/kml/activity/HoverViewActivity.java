package com.reversecoder.kml.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.customviewanimations.library.Techniques;
import com.customviewanimations.library.YoYo;
import com.reversecoder.library.customview.androidviewhover.BlurLayout;
import com.reversecoder.kml.R;

public class HoverViewActivity extends ActionBarActivity {

    private Context mContext;
    private BlurLayout mSampleLayout, mSampleLayout2, mSampleLayout3, mSampleLayout4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_hover);
        BlurLayout.setGlobalDefaultDuration(450);
        mSampleLayout = (BlurLayout)findViewById(R.id.blur_layout);
        View hover = LayoutInflater.from(mContext).inflate(R.layout.hover_sample, null);
        hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Tada)
                    .duration(550)
                    .playOn(v);
            }
        });
        hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Swing)
                        .duration(550)
                        .playOn(v);
            }
        });

        mSampleLayout.setHoverView(hover);
        mSampleLayout.setBlurDuration(550);
        mSampleLayout.addChildAppearAnimator(hover, hover.findViewById(R.id.heart), Techniques.FlipInX, 550, 0);
        mSampleLayout.addChildAppearAnimator(hover, hover.findViewById(R.id.share), Techniques.FlipInX, 550, 250);
        mSampleLayout.addChildAppearAnimator(hover, hover.findViewById(R.id.more), Techniques.FlipInX, 550, 500);

        mSampleLayout.addChildDisappearAnimator(hover, hover.findViewById(R.id.heart), Techniques.FlipOutX, 550, 500);
        mSampleLayout.addChildDisappearAnimator(hover, hover.findViewById(R.id.share), Techniques.FlipOutX, 550, 250);
        mSampleLayout.addChildDisappearAnimator(hover, hover.findViewById(R.id.more), Techniques.FlipOutX, 550, 0);

        mSampleLayout.addChildAppearAnimator(hover, hover.findViewById(R.id.description), Techniques.FadeInUp);
        mSampleLayout.addChildDisappearAnimator(hover, hover.findViewById(R.id.description), Techniques.FadeOutDown);

        //sample 2

        mSampleLayout2 = (BlurLayout)findViewById(R.id.blur_layout2);
        View hover2 = LayoutInflater.from(mContext).inflate(R.layout.hover_sample2, null);
        hover2.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Pretty Cool, Right?", Toast.LENGTH_SHORT).show();
            }
        });
        mSampleLayout2.setHoverView(hover2);

        mSampleLayout2.addChildAppearAnimator(hover2, hover2.findViewById(R.id.description), Techniques.FadeInUp);
        mSampleLayout2.addChildDisappearAnimator(hover2, hover2.findViewById(R.id.description), Techniques.FadeOutDown);
        mSampleLayout2.addChildAppearAnimator(hover2, hover2.findViewById(R.id.avatar), Techniques.DropOut, 1200);
        mSampleLayout2.addChildDisappearAnimator(hover2, hover2.findViewById(R.id.avatar), Techniques.FadeOutUp);
        mSampleLayout2.setBlurDuration(1000);

        //sample3
        mSampleLayout3 = (BlurLayout)findViewById(R.id.blur_layout3);
        View hover3 = LayoutInflater.from(mContext).inflate(R.layout.hover_sample3, null);
        mSampleLayout3.setHoverView(hover3);
        mSampleLayout3.addChildAppearAnimator(hover3, hover3.findViewById(R.id.eye), Techniques.Landing);
        mSampleLayout3.addChildDisappearAnimator(hover3, hover3.findViewById(R.id.eye), Techniques.TakingOff);
        mSampleLayout3.enableZoomBackground(true);
        mSampleLayout3.setBlurDuration(1200);

        //sample 4

        mSampleLayout4 = (BlurLayout)findViewById(R.id.blur_layout4);
        View hover4 = LayoutInflater.from(mContext).inflate(R.layout.hover_sample4,null);
        mSampleLayout4.setHoverView(hover4);
        mSampleLayout4.addChildAppearAnimator(hover4, hover4.findViewById(R.id.cat), Techniques.SlideInLeft);
        mSampleLayout4.addChildAppearAnimator(hover4, hover4.findViewById(R.id.mail), Techniques.SlideInRight);

        mSampleLayout4.addChildDisappearAnimator(hover4, hover4.findViewById(R.id.cat), Techniques.SlideOutLeft);
        mSampleLayout4.addChildDisappearAnimator(hover4, hover4.findViewById(R.id.mail), Techniques.SlideOutRight);

        mSampleLayout4.addChildAppearAnimator(hover4, hover4.findViewById(R.id.content), Techniques.BounceIn);
        mSampleLayout4.addChildDisappearAnimator(hover4, hover4.findViewById(R.id.content), Techniques.FadeOutUp);


        hover4.findViewById(R.id.cat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/daimajia"));
                startActivity(getWebPage);
            }
        });

        hover4.findViewById(R.id.mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"daimajia@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "About AndroidViewHover");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "I have a good idea about this project..");

                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
    }
}
