package com.reversecoder.kml.viewholder;

/**
 * Created by Rashed on 7/15/2016.
 */

import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.reversecoder.kml.R;
import com.reversecoder.kml.model.AdMob;

public class AdMobViewHolder extends BaseViewHolder<AdMob> {

//    public TextView txtAdMobName;
    public AdView adMobView;


    public AdMobViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_admob);

//        txtAdMobName = $(R.id.txt_admob_name);
        adMobView=$(R.id.adView);
    }

    @Override
    public void setData(final AdMob adMob){
//        mTv_name.setText(person.getName());
//        mTv_sign.setText(person.getSign());
//        Glide.with(getContext())
//                .load(person.getFace())
//                .placeholder(R.drawable.default_image)
//                .bitmapTransform(new CropCircleTransformation(getContext()))
//                .into(mImg_face);
//        txtAdMobName.setText(adMob.getAdName());
//        Picasso.with(getContext()).load(adMob.getAdUrl()).transform(new CropSquareTransformation()).into(imgMoviePoster);

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adMobView.loadAd(adRequest);
    }
}

