package com.example.seoulo1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class LocationAdapter extends ArrayAdapter<LocationItem> {
    private Context context;
    private List mList;
    private ListView mListView;

    class UserViewHolder {
        public TextView category_name;
        public TextView name;
        public TextView distance;
        public TextView vicinity, open_now, rating;
    }

    public LocationAdapter(Context context, List<LocationItem> list, ListView listView) {
        super(context, 0, list);
        this.context = context;
        this.mList = list;
        this.mListView = listView;
    }

    // 한 줄(Row)를 위한 뷰(View)를 재사용하여 ListIView의 효율성을 올립니다.
    @NonNull
    @Override
    public View getView(int position,       // LIstView에 보여지게 되는 데이터인 Voca 객체 리스트의 인덱스

                        View convertView,   // 주어진 데이터를 보여주기 위해 사용될 한 줄(row)을 위한 뷰(View)
                        // 값이 null인 경우에만 새로 생성하고 그 외에는 재사용됩니다.

                        @NonNull ViewGroup parentViewGroup  // XML 레이아웃 파일을 View 객체로 변환하기 위해 사용되는 부모 뷰그룹
    ) {
        View rowView = convertView; // 코드 가독성을 위해서 rowView 변수를 사용합니다.
        UserViewHolder viewHolder;
        String Status;
        if (rowView == null) {

            // 레이아웃을 정의한 XML 파일(R.layout.list_item)을 읽어서 계층 구조의 뷰 객체(rowView)로 변환합니다.
            // rowView 객체는 3개의 TextView로 구성되어 있습니다.
            //
            // 다음 한줄로 구현도 가능합니다.
            // rowView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parentViewGroup, false);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            rowView = layoutInflater.inflate(R.layout.list_view_items, parentViewGroup, false);


            // view holder의 구성 요소의 값과 한 줄을 구성하는 레이아웃을 연결함.
            //
            // rowView(=R.layout.list_item)에서 주어진 ID(R.id.textview_list_english)를 가진 뷰를 찾습니다.
            // 찾는 뷰의 타입에 따라 findViewById 리턴 결과를 타입 변환해줘야 합니다.
            viewHolder = new UserViewHolder();
            viewHolder.name = (TextView) rowView.findViewById(
                    R.id.textview_name // 한 줄에 대한 레이아웃 파일(R.layout.list_item)의 구성요소,
            );
            //viewHolder.category_name = (TextView) rowView.findViewById(R.id.textview_category_name);
            viewHolder.vicinity = (TextView) rowView.findViewById(R.id.textview_vicinity);
            viewHolder.distance = (TextView) rowView.findViewById(R.id.textview_distance);
            viewHolder.open_now = (TextView) rowView.findViewById(R.id.textview_open_now);
            viewHolder.rating = (TextView) rowView.findViewById(R.id.textview_rating);

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
        LocationItem locationItem = (LocationItem) mList.get(position);
        //현재 선택된 Vocal 객체를 화면에 보여주기 위해서 앞에서 미리 찾아 놓은 뷰에 데이터를 집어넣습니다.
        viewHolder.name.setText(locationItem.getLName());
        //viewHolder.category_name.setText(locationItem.getCategory_name());
        //viewHolder.itemIndex.setText("Voca[" + position + "]");
        viewHolder.vicinity.setText(locationItem.getVicinity());
        String dis=locationItem.getDistance() + "m";
        viewHolder.distance.setText(dis);
        viewHolder.open_now.setText(locationItem.getOpen_now());
        viewHolder.rating.setText(locationItem.getRating());
        this.notifyDataSetChanged();

        Log.d("@@@", "locationitem[" + position + "]" + " row view is " + Status + ", tag = " + tag);


        // 화면에 보여질 뷰를 리턴하여 ListView의 특정 줄로 보여지게 합니다.
        //본 예제에서는 5개의 TextView 구성되어 있습니다.
        return rowView;

    }
}