package com.reversecoder.kml.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reversecoder.library.customview.bang.SmallBang;
import com.reversecoder.library.customview.bang.SmallBangListener;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.reversecoder.kml.R;
import com.reversecoder.kml.activity.HomeActivity;
import com.reversecoder.kml.model.Movies;
import com.reversecoder.kml.util.CropSquareTransformation;
import com.squareup.picasso.Picasso;

public class MovieViewHolder extends BaseViewHolder<Movies> {
    public ImageView imgMoviePoster;
    public TextView txtMovieName,txtMovieRating,txtMovieType,txtMovieReleaseDate;
    public ImageView btnLike;
    private SmallBang mSmallBang;
//    public BlurLayout mBlurLayout;

    public MovieViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_movies);

        imgMoviePoster = $(R.id.item_imageview);
        txtMovieName = $(R.id.item_textview);
//        mBlurLayout = $(R.id.blur_layout);
        txtMovieRating=$(R.id.md_imdb_rating);
        txtMovieType=$(R.id.md_type);
        txtMovieReleaseDate=$(R.id.md_release_date);
        btnLike=$(R.id.btn_like);

    }

    @Override
    public void setData(final Movies movie){

        txtMovieName.setText(movie.getMovieName());
        txtMovieRating.setText(movie.getImdbRating());
        txtMovieType.setText(movie.getMovieType());
        txtMovieReleaseDate.setText(movie.getReleaseDate());
        Picasso.with(getContext()).load(movie.getPosterUrl()).transform(new CropSquareTransformation()).into(imgMoviePoster);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getContext()).openDraggableView(movie);
            }
        });

        if(mSmallBang==null){
            mSmallBang=((HomeActivity)getContext()).getBang(getContext());
        }
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movie.isLiked()){
                    btnLike.setBackgroundResource(R.drawable.heart_grey);
                    movie.setLiked(false);
                }else{
                    btnLike.setBackgroundResource(R.drawable.heart_red_fill);
                    movie.setLiked(true);
                }
                mSmallBang.bang(v);
                mSmallBang.setmListener(new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {
                    }

                    @Override
                    public void onAnimationEnd() {
                    }
                });
            }
        });

        //set hover view
//        BlurLayout.setGlobalDefaultDuration(450);
//        View hover = LayoutInflater.from(getContext()).inflate(R.layout.hover_item_movie, null);
//        hover.findViewById(R.id.heart).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                YoYo.with(Techniques.Tada)
//                        .duration(550)
//                        .playOn(v);
//            }
//        });
//        hover.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                YoYo.with(Techniques.Swing)
//                        .duration(550)
//                        .playOn(v);
//                ((HomeActivity) getContext()).openDraggableView(movie);
//            }
//        });
//        mBlurLayout.setHoverView(hover);
//        mBlurLayout.setBlurDuration(550);
//        mBlurLayout.addChildAppearAnimator(hover, hover.findViewById(R.id.heart), Techniques.FlipInX, 550, 0);
//        mBlurLayout.addChildAppearAnimator(hover, hover.findViewById(R.id.share), Techniques.FlipInX, 550, 250);
//        mBlurLayout.addChildAppearAnimator(hover, hover.findViewById(R.id.more), Techniques.FlipInX, 550, 500);
//
//        mBlurLayout.addChildDisappearAnimator(hover, hover.findViewById(R.id.heart), Techniques.FlipOutX, 550, 500);
//        mBlurLayout.addChildDisappearAnimator(hover, hover.findViewById(R.id.share), Techniques.FlipOutX, 550, 250);
//        mBlurLayout.addChildDisappearAnimator(hover, hover.findViewById(R.id.more), Techniques.FlipOutX, 550, 0);
//
//        mBlurLayout.addChildAppearAnimator(hover, hover.findViewById(R.id.description), Techniques.FadeInUp);
//        mBlurLayout.addChildDisappearAnimator(hover, hover.findViewById(R.id.description), Techniques.FadeOutDown);
//
//        try {
//            mBlurLayout.dismissHover(1, hover, Techniques.FlipOutX, 1, 0);
//        } catch (Exception e) {
//        }
    }
}

