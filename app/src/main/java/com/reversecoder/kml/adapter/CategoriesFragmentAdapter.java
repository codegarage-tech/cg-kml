package com.reversecoder.kml.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.reversecoder.kml.activity.CategoryWiseMovieListActivity;
import com.reversecoder.kml.model.Categories;

import java.util.ArrayList;

/**
 * Created by alam on 6/15/16.
 */
public class CategoriesFragmentAdapter extends RecyclerView.Adapter<CategoriesFragmentAdapter.ViewHolder> {

    private Context mContext;
    //        private List<String> mItems;
    private ArrayList<Categories> mItems=new ArrayList<Categories>();
    private final TypedArray mColors;

    public CategoriesFragmentAdapter(Context context, ArrayList<Categories> items) {
        mContext = context;
        mItems = items;
        final Resources mRes = context.getResources();
        mColors = mRes.obtainTypedArray(R.array.letter_tile_colors);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_categories,
                parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String text=mItems.get(position).getTitle();
        holder.itemView.setBackgroundColor(getColor(text));
        holder.textView.setText(text);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CategoryWiseMovieListActivity.class);
                mContext.startActivity(intent);

                Toast.makeText(mContext,(position+1)+ " no movie is: "+text,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    private int getColor(String key) {
        final int color = Math.abs(key.hashCode()) % mColors.length();
        return mColors.getColor(color, Color.BLUE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textview);
        }
    }
}
