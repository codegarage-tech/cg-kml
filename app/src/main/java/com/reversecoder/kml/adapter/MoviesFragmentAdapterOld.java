//package com.reversecoder.kml.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.graphics.Color;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.reversecoder.ml.R;
//import com.reversecoder.kml.activity.HomeActivity;
//import com.reversecoder.kml.model.Movies;
//import com.reversecoder.kml.util.AllConstants;
//import com.reversecoder.kml.util.CropSquareTransformation;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
///**
// * Created by alam on 6/15/16.
// */
//public class MoviesFragmentAdapterOld extends RecyclerView.Adapter<MoviesFragmentAdapter.ViewHolder> {
//
//    private Context mContext;
//    //    private List<String> mItems;
//    private ArrayList<Movies> mItems = new ArrayList<Movies>();
//
//    private final TypedArray mColors;
//
//    public MoviesFragmentAdapterOld(Context context, ArrayList<Movies> items) {
//        mContext = context;
//        mItems = items;
//        final Resources mRes = context.getResources();
//        mColors = mRes.obtainTypedArray(R.array.letter_tile_colors);
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_movies,
//                parent, false);
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        final Movies movies=mItems.get(position);
//        final String text = mItems.get(position).getMovieName();
//        holder.itemView.setBackgroundColor(getColor(text));
//        holder.textView.setText(text);
//
//        Picasso.with(mContext).load(movies.getPosterUrl()).transform(new CropSquareTransformation()).into(holder.imageView);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((HomeActivity)mContext).openDraggableView(movies);
////                Intent intent = new Intent(mContext, MovieDetailActivity.class);
////                intent.putExtra(AllConstants.INTENT_KEY_MOVIE_DETAIL,movies.toStringObject());
////                mContext.startActivity(intent);
//
////                Toast.makeText(mContext,(position+1)+ " no movie is: "+text,Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
//
//    private int getColor(String key) {
//        final int color = Math.abs(key.hashCode()) % mColors.length();
//        return mColors.getColor(color, Color.BLUE);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mItems == null ? 0 : mItems.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder  {
//
//        public View itemView;
//        public TextView textView;
//        public ImageView imageView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            this.itemView = itemView;
//            textView = (TextView) itemView.findViewById(R.id.item_textview);
//            imageView = (ImageView) itemView.findViewById(R.id.item_imageview);
//        }
//    }
//}
