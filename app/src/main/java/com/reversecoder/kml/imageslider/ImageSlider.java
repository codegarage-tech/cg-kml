package com.reversecoder.kml.imageslider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.reversecoder.kml.R;
import com.reversecoder.kml.activity.HomeActivity;
import com.reversecoder.kml.model.SliderModel;
import com.reversecoder.kml.util.CropSquareTransformation;
import com.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Picasso;

public class ImageSlider extends BaseSliderView {

    SliderModel myData;

    public ImageSlider(Context context) {
        super(context);
    }

    public ImageSlider(Context context, SliderModel dm) {
        super(context);
        myData = dm;
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(
                R.layout.image_slider_item, null);
        ImageView target = (ImageView) v.findViewById(R.id._image);
//        target.setImageBitmap(myData.getImageBitmap());
        Picasso.with(mContext).load(myData.getImageURL()).transform(new CropSquareTransformation()).into(target);
        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(myData.getTitle());
        v.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),myData.getTitle(),Toast.LENGTH_SHORT).show();
                ((HomeActivity)getContext()).resumeImageSlider();
            }
        });

        return v;
    }
}
