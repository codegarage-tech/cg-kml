package com.reversecoder.kml.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.reversecoder.kml.R;
import com.reversecoder.kml.model.Movies;
import com.reversecoder.kml.util.AllConstants;
import com.reversecoder.kml.util.CropSquareTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alam on 6/15/16.
 */
public class MoviesFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    //    private List<String> mItems;
    private ArrayList<Movies> mItems = new ArrayList<Movies>();

    private final TypedArray mColors;

    public MoviesFragmentAdapter(Context context, ArrayList<Movies> items) {
        mContext = context;
        mItems = items;
        final Resources mRes = context.getResources();
        mColors = mRes.obtainTypedArray(R.array.letter_tile_colors);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_movies,
//                parent, false);
//        return new ViewHolder(itemView);

        View itemView = null;
        RecyclerView.ViewHolder itemViewHolder = null;
        if (viewType == AllConstants.AD_TYPE) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_admob,
                    parent, false);
            itemViewHolder = new AdViewHolder(itemView);

        } else {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.item_movies,
                    parent, false);
            itemViewHolder = new ContentViewHolder(itemView);
        }
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case AllConstants.CONTENT_TYPE:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                final Movies movies = mItems.get(position);
                final String text = mItems.get(position).getMovieName();
                contentViewHolder.itemView.setBackgroundColor(getColor(text));
                contentViewHolder.textView.setText(text);

                Picasso.with(mContext).load(movies.getPosterUrl()).transform(new CropSquareTransformation()).into(contentViewHolder.imageView);

//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ((HomeActivity) mContext).openDraggableView(movies);
//                    }
//                });

                break;

            case AllConstants.AD_TYPE:
                AdViewHolder adViewHolder=(AdViewHolder)holder;
                AdRequest adRequest = new AdRequest.Builder()
                        .build();
                adViewHolder.mAdView.loadAd(adRequest);
                break;
        }
    }

    private int getColor(String key) {
        final int color = Math.abs(key.hashCode()) % mColors.length();
        return mColors.getColor(color, Color.BLUE);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
//        return mItems.size()+ (mItems.size() % 5);
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems.get(position) == null) {
//        if (position % 5 == 0){
            return AllConstants.AD_TYPE;
        } else {
            return AllConstants.CONTENT_TYPE;
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView textView;
        public ImageView imageView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = (TextView) itemView.findViewById(R.id.item_textview);
            imageView = (ImageView) itemView.findViewById(R.id.item_imageview);
        }
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public AdView mAdView;

        public AdViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mAdView = (AdView) itemView.findViewById(R.id.adView);
        }
    }
}