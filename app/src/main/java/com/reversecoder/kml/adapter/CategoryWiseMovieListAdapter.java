package com.reversecoder.kml.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.reversecoder.library.customview.event.OnSingleClickListener;
import com.reversecoder.kml.R;
import com.reversecoder.kml.model.Movies;
import com.reversecoder.kml.util.AllConstants;
import com.reversecoder.kml.util.AllEnumerations;

import java.util.ArrayList;

public class CategoryWiseMovieListAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<Movies> mData;
    private static LayoutInflater inflater = null;
    private int selectedItemPosition = -1; // no item selected by default
    AllEnumerations.SORT_ORDER sortOrder = AllEnumerations.SORT_ORDER.ASCENDING;
    AllEnumerations.SORT_BY sortBy = AllEnumerations.SORT_BY.NAME;
    private TypedArray mColors=null;

    public CategoryWiseMovieListAdapter(Activity activity) {
        mActivity = activity;
        mData = new ArrayList<Movies>();
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AllConstants.DETAULT_LIST_ITEM_SELECTED_POSITION = selectedItemPosition;

    }

    public ArrayList<Movies> getData() {
        return mData;
    }

    public void setData(ArrayList<Movies> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Movies getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.item_movies, null);

        final Movies movie = getItem(position);

        TextView movieTitle=(TextView) vi.findViewById(R.id.item_textview);
        movieTitle.setText(movie.getMovieName());
        vi.setBackgroundColor(getColor(mActivity,movie.getMovieName()));
//        ((TextView) vi.findViewById(R.id.txt_address_title)).setText(address.getAddress_title());
//        ((TextView) vi.findViewById(R.id.txt_sender)).setText(message.getSenderName());
//        ((TextView) vi.findViewById(R.id.txt_send_date)).setText(message.getCreated());
//        ((TextView) vi.findViewById(R.id.txt_message)).setText(message.getMessage());
//        ((TextView) vi.findViewById(R.id.txt_postal_code)).setText(address.getPostal_code());
//        RadioButton rbtnTick = (RadioButton) vi.findViewById(R.id.rbtn_address_tick);
//
//        if (selectedItemPosition == position) {
//            rbtnTick.setChecked(true);
//            rbtnTick.setVisibility(View.VISIBLE);
//        } else {
//            rbtnTick.setChecked(false);
//            rbtnTick.setVisibility(View.INVISIBLE);
//        }

//        if (message.getIs_read().equalsIgnoreCase("1")) {
//            highLightRow(vi);
//        }

        vi.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
//                if (!NetworkManager.isConnected(mActivity)) {
//                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.alert_network_error), Toast.LENGTH_SHORT).show();
//                } else {
//                Intent messageDetailIntent = new Intent(mActivity, MessageDetailActivity.class);
//                messageDetailIntent.putExtra(AllConstants.INTENT_KEY_MESSAGE_DETAIL, message.toStringObject());
//                messageDetailIntent.putExtra(AllConstants.INTENT_KEY_MESSAGE_DETAIL_FROM_INBOX, true);
//                messageDetailIntent.putExtra(AllConstants.INTENT_KEY_MESSAGE_DETAIL_FROM_OUTBOX, false);
//                mActivity.startActivity(messageDetailIntent);
//                NotificationUtilManager.getNotificationManager(mActivity).cancel(NotificationUtilManager.NOTIFICATION_ID);
//                }
            }
        });

        return vi;
    }

    public void setSelectedItem(ViewGroup view, int postion) {
        ListView listView = (ListView) view;
        selectedItemPosition = postion;
        AllConstants.DETAULT_LIST_ITEM_SELECTED_POSITION = postion;
        notifyDataSetChanged();
        listView.smoothScrollToPosition(postion);
    }

    public void highLightRow(View view) {
        view.setBackgroundResource(R.color.grey_bg);
        notifyDataSetChanged();
    }

//    public void sortMovies(AllEnumerations.SORT_BY sortby, AllEnumerations.SORT_ORDER sortorder) {
//        sortBy=sortby;
//        sortOrder = sortorder;
//        sortData(mData,sortBy,sortOrder);
//        notifyDataSetChanged();
//    }
//
//    private ArrayList<Movies> sortData(ArrayList<Movies> data, AllEnumerations.SORT_BY sortby, AllEnumerations.SORT_ORDER sortorder) {
//        final ArrayList<Movies> sortedData = data;
//        Collections.sort(sortedData, new Comparator<Movies>() {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date1;
//            Date date2;
//
//            public int compare(Movies msg1, Movies msg2) {
//                try {
//                    date1 = dateFormat.parse(msg1.getCreated());
//                    date2 = dateFormat.parse(msg2.getCreated());
//                } catch (Exception ex) {
//                }
//                if (date1 == null || date2 == null) {
//                    return 0;
//                } else {
//                    if (sortOrder == AllEnumerations.SORT_ORDER.ASCENDING) {
//                        return date1.compareTo(date2);
//                    } else {
//                        return date2.compareTo(date1);
//                    }
//                }
//            }
//        });
//        return sortedData;
//    }

    private int getColor(Context context,String key) {
        if(mColors==null){
            final Resources mRes = context.getResources();
            mColors = mRes.obtainTypedArray(R.array.letter_tile_colors);
        }
        final int color = Math.abs(key.hashCode()) % mColors.length();
        return mColors.getColor(color, Color.BLUE);
    }
}