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
import androidx.annotation.Nullable;

import java.util.List;

public class myLocationAdapter extends ArrayAdapter<LocationItem> implements View.OnClickListener{
    private ListBtnClickListener listBtnClickListener;
    private ImageView like;
    private Context context;
    private List<LocationItem> mList;
    private ListView mListView;

    public interface ListBtnClickListener {
        void onListButtonClick(int position, int resourceid) ;

    }
    public myLocationAdapter(@NonNull Context context, int resource, @NonNull List<LocationItem> objects, ListBtnClickListener listBtnClickListener, ListView listView) {
        super(context, resource, objects);
        this.context = context;
        this.listBtnClickListener = listBtnClickListener;
        this.mList = objects;
        this.mListView = listView;
    }
    class UserViewHolder {
        public TextView category_name;
        public TextView name;
        public TextView distance;
        public TextView vicinity, open_now, rating;
        public ImageView like;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int pos = position;
        View rowView = convertView; // 코드 가독성을 위해서 rowView 변수를 사용합니다.
        UserViewHolder viewHolder;
        String Status;
        if (rowView == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.list_view_items, parent, false);


            // view holder의 구성 요소의 값과 한 줄을 구성하는 레이아웃을 연결함.
            //
            // rowView(=R.layout.list_item)에서 주어진 ID(R.id.textview_list_english)를 가진 뷰를 찾습니다.
            // 찾는 뷰의 타입에 따라 findViewById 리턴 결과를 타입 변환해줘야 합니다.
            viewHolder = new UserViewHolder();
            viewHolder.name = (TextView) rowView.findViewById(
                    R.id.textview_name // 한 줄에 대한 레이아웃 파일(R.layout.list_item)의 구성요소,
            );
            //viewHolder.category_name = (TextView) rowView.findViewById(R.id.textview_category_name);
            viewHolder.vicinity = rowView.findViewById(R.id.textview_vicinity);
            viewHolder.distance = rowView.findViewById(R.id.textview_distance);
            viewHolder.open_now = rowView.findViewById(R.id.textview_open_now);
            viewHolder.rating = rowView.findViewById(R.id.textview_rating);
            viewHolder.like = rowView.findViewById(R.id.like);

            rowView.setTag(viewHolder);

            Status = "created";
        } else {

            viewHolder = (UserViewHolder) rowView.getTag();

            Status = "reused";
        }
        // 태그 분석을 위한 코드 시작
        String Tag = rowView.getTag().toString();
        int idx = Tag.indexOf("@");
        String tag = Tag.substring(idx + 1);

        //Voca 객체 리스트의 position 위치에 있는 Voca 객체를 가져옵니다.
        LocationItem locationItems = mList.get(position);
        //현재 선택된 Vocal 객체를 화면에 보여주기 위해서 앞에서 미리 찾아 놓은 뷰에 데이터를 집어넣습니다.
        viewHolder.name.setText(locationItems.getLName());
        //viewHolder.category_name.setText(locationItem.getCategory_name());
        //viewHolder.itemIndex.setText("Voca[" + position + "]");
        viewHolder.vicinity.setText(locationItems.getVicinity());
        String dis=locationItems.getDistance() + "m";
        viewHolder.distance.setText(dis);
        viewHolder.open_now.setText(locationItems.getOpen_now());
        viewHolder.rating.setText(locationItems.getRating());
        viewHolder.like.setOnClickListener(this);
        viewHolder.like.setTag(pos); // position을 tag로 설정
        //viewHolder.like.setVisibility(View.VISIBLE);
        if (locationItems.getStatus()) {
            viewHolder.like.setImageResource(R.drawable.filled_heart);
        } else {
            viewHolder.like.setImageResource(R.drawable.nonfilled_heart);
        }

        //this.notifyDataSetChanged();

        Log.d("@@@", "locationitem[" + position + "]" + " row view is " + Status + ", tag = " + tag);


        // 화면에 보여질 뷰를 리턴하여 ListView의 특정 줄로 보여지게 합니다.
        //본 예제에서는 5개의 TextView 구성되어 있습니다.
        return rowView;



    }
    @Override
    public void onClick(View view) {
        Log.d(TAG, "like 버튼  onClick: ");
        if (this.listBtnClickListener != null) {
            Log.d(TAG, "like 버튼  onClick 들어옴 !!!!!!!! ");

            int position = (int) view.getTag();
            int resourceId = view.getId();

            this.listBtnClickListener.onListButtonClick(position, resourceId) ;
        }
    }
}
