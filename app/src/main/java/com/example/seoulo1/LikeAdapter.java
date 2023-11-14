package com.example.seoulo1;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class LikeAdapter extends ArrayAdapter<LocationItem> implements View.OnClickListener {
    private Context context;
    private List<LocationItem> likedLocations;

    private LikeListBtnClickListener likeListBtnClickListener;
    private ImageView like;
    private ListView mListView;
    public interface LikeListBtnClickListener {
        void onLikeListButtonClick(int position, int resourceid) ;

    }


    public LikeAdapter(@NonNull Context context, int resource, @NonNull List<LocationItem> objects, LikeAdapter.LikeListBtnClickListener likeListBtnClickListener, ListView listView) {
        super(context, resource, objects );
        this.context = context;
        this.likeListBtnClickListener = likeListBtnClickListener;
        this.likedLocations = objects;
        this.mListView = listView;
    }

   // @Override
//    public int getCount() {
//        return likedLocations.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return likedLocations.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.like_items, parent, false);
            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.textview_name);
            holder.openNowTextView = convertView.findViewById(R.id.textview_open_now);
            holder.ratingTextView = convertView.findViewById(R.id.textview_rating);
            holder.vicinityTextView = convertView.findViewById(R.id.textview_vicinity);
            holder.distanceTextView = convertView.findViewById(R.id.textview_distance);
            holder.likeImageView = convertView.findViewById(R.id.like);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 데이터 설정
        LocationItem locationItem = likedLocations.get(position);
        holder.nameTextView.setText(locationItem.getLName());
        holder.openNowTextView.setText(locationItem.getOpen_now());
        holder.ratingTextView.setText(locationItem.getRating());
        holder.vicinityTextView.setText(locationItem.getVicinity());
        holder.distanceTextView.setText(String.valueOf(locationItem.getDistance()));

        // "좋아요" 이미지 설정
        if (locationItem.getStatus()) {
            holder.likeImageView.setImageResource(R.drawable.filled_heart);
        } else {
            holder.likeImageView.setImageResource(R.drawable.nonfilled_heart);
        }
        holder.likeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (likeListBtnClickListener != null) {
                    likeListBtnClickListener.onLikeListButtonClick(position, view.getId());
                }
            }
        });
        return convertView;
    }


    private static class ViewHolder {
        TextView nameTextView;
        TextView openNowTextView;
        TextView ratingTextView;
        TextView vicinityTextView;
        TextView distanceTextView;
        ImageView likeImageView;
    }
    @Override
    public void onClick(View view) {
        Log.d(TAG, "like 버튼  onClick: ");
        if (this.likeListBtnClickListener!= null) {
            Log.d(TAG, "like 버튼  onClick 들어옴 !!!!!!!! ");

            int position = (int) view.getTag();
            int resourceId = view.getId();
            this.likeListBtnClickListener.onLikeListButtonClick(position, resourceId) ;
        }
    }
}
