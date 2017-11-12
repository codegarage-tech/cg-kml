package com.reversecoder.kml.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.reversecoder.kml.R;
import com.reversecoder.kml.model.Stars;

import java.util.ArrayList;

/**
 * Created by alam on 6/15/16.
 */
public class StarsFragmentAdapter extends RecyclerView.Adapter<StarsFragmentAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Stars> mItems = new ArrayList<Stars>();

    private final TypedArray mColors;

    public StarsFragmentAdapter(Context context, ArrayList<Stars> items) {
        mContext = context;
        mItems = items;
        final Resources mRes = context.getResources();
        mColors = mRes.obtainTypedArray(R.array.letter_tile_colors);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_subtitle,
                parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String text = mItems.get(position).getTitle();
        holder.itemView.setBackgroundColor(getColor(text));
        holder.textView.setText(text);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, (position + 1) + " no item is: " + text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getColor(String key) {
        final int color = Math.abs(key.hashCode()) % mColors.length();
        return mColors.getColor(color, Color.BLUE);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = (TextView) itemView.findViewById(R.id.item_textview);
        }
    }
}
