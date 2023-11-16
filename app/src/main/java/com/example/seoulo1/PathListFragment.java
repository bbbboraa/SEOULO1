package com.example.seoulo1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class PathListFragment extends Fragment {
    private int position;  // 날짜에 해당하는 위치
    private long daysDiff;  // 사용자가 선택한 날짜


    private static final int MAX_ADDRESS_COUNT = 3;
    private RecyclerView rv1, rv2, rv3, rv4, rv5;
    private AddressAdapter rv1Adapter, rv2Adapter, rv3Adapter, rv4Adapter, rv5Adapter;
    private ArrayList<String> rv1DataList, rv2DataList, rv3DataList, rv4DataList, rv5DataList;

    private RecyclerView.LayoutManager rv1LayoutManager, rv2LayoutManager, rv3LayoutManager, rv4LayoutManager, rv5LayoutManager;


    private ImageButton like_btn, menu_btn;


    // 생성자
    public PathListFragment() {
        // 빈 생성자가 필요합니다.
    }

    // 프래그먼트를 생성하는 메서드
    // 'PathListFragment 클래스는 Android Fragment 클래스를 확장한 것이며
    // 화면에 표시되는 하위 경로 목록을 관리합니다. 여러 멤버 변수와 메소드가 있습니다.
    public static PathListFragment newInstance(int position, long daysDiff) {
        PathListFragment fragment = new PathListFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putLong("daysDiff", daysDiff); //'daysDiff' 값을 사용하여 'daysDiff' 설정
        fragment.setArguments(args);
        return fragment;
    }


    // 'onCreate' 메서드는 Fragment가 생성될 때 호출되는 메서드이며
    // 'position' 및 'daysDiff'와 같은 인수를 확인합니다.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            position = getArguments().getInt("position");
            daysDiff = getArguments().getLong("daysDiff");  // 'daysDiff' 값에서 데이터를 가져옴
        }
    }


    // 'onCreateView' 메서드는 Fragment의 레이아웃을 설정하고
    // 날짜 및 위치 정보를 표시하는 메서드입니다.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 여기에서 프래그먼트 레이아웃을 설정하고 날짜에 해당하는 데이터를 표시합니다.
        View view = inflater.inflate(R.layout.fragment_path_list, container, false);
        TextView textView = view.findViewById(R.id.calendar_day);


        // 선택된 날짜와 위치를 표시
        textView.setText("day " + (position + 1));


        // RecyclerView 및 관련 어댑터 및 데이터 목록을 초기화
        rv1 = view.findViewById(R.id.rv1);
        rv2 = view.findViewById(R.id.rv2);
        rv3 = view.findViewById(R.id.rv3);
        rv4 = view.findViewById(R.id.rv4);
        rv5 = view.findViewById(R.id.rv5);

        // 데이터베이스 객체를 먼저 생성
        AppDatabase database = Room.databaseBuilder(requireContext(), AppDatabase.class, "places-db")
                .fallbackToDestructiveMigration()
                .build();

        rv1LayoutManager = new LinearLayoutManager(requireContext());
        rv2LayoutManager = new LinearLayoutManager(requireContext());
        rv3LayoutManager = new LinearLayoutManager(requireContext());
        rv4LayoutManager = new LinearLayoutManager(requireContext());
        rv5LayoutManager = new LinearLayoutManager(requireContext());

        rv1DataList = new ArrayList<>();
        rv2DataList = new ArrayList<>();
        rv3DataList = new ArrayList<>();
        rv4DataList = new ArrayList<>();
        rv5DataList = new ArrayList<>();

        // RecyclerView 및 관련 어댑터, 레이아웃 매니저를 설정
        rv1.setLayoutManager(rv1LayoutManager);
        rv1Adapter = new AddressAdapter(database, rv1DataList);
        rv1.setAdapter(rv1Adapter);

        rv2.setLayoutManager(rv2LayoutManager);
        rv2Adapter = new AddressAdapter(database, rv2DataList);
        rv2.setAdapter(rv2Adapter);

        rv3.setLayoutManager(rv3LayoutManager);
        rv3Adapter = new AddressAdapter(database, rv3DataList);
        rv3.setAdapter(rv3Adapter);

        rv4.setLayoutManager(rv4LayoutManager);
        rv4Adapter = new AddressAdapter(database, rv4DataList);
        rv4.setAdapter(rv4Adapter);

        rv5.setLayoutManager(rv5LayoutManager);
        rv5Adapter = new AddressAdapter(database, rv5DataList);
        rv5.setAdapter(rv5Adapter);


        // 버튼 클릭 이벤트를 처리하고 주소 검색 액티비티를 시작하는 버튼을 설정
        Button btn_AddrReserch_move = view.findViewById(R.id.btn_AddrReserch_move);
        Button btn_AddrReserch_move2 = view.findViewById(R.id.btn_AddrReserch_move2);
        Button btn_AddrReserch_move3 = view.findViewById(R.id.btn_AddrReserch_move3);
        Button btn_AddrReserch_move4 = view.findViewById(R.id.btn_AddrReserch_move4);
        Button btn_AddrReserch_move5 = view.findViewById(R.id.btn_AddrReserch_move5);
        Button Route_Recomm = view.findViewById(R.id.Route_Recomm);

        btn_AddrReserch_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddressSearch(1);
            }
        });

        btn_AddrReserch_move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddressSearch(2);
            }
        });

        btn_AddrReserch_move3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddressSearch(3);
            }
        });

        btn_AddrReserch_move4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddressSearch(4);
            }
        });

        btn_AddrReserch_move5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddressSearch(5);
            }
        });

        Route_Recomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 루트 추천 버튼 클릭 시 실행할 로직 추가
                Intent intent = new Intent(requireContext(), AddrSetActivity.class);
                startActivity(intent);
            }
        });


        // 나머지 버튼과 메뉴 버튼 관련 코드 추가
        like_btn = view.findViewById(R.id.like_btn);
        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 루트 추천 버튼 클릭 시 실행할 로직 추가
                Intent intent = new Intent(requireContext(), LikeActivity.class);
                startActivity(intent);
            }
        });

        menu_btn = view.findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(this::showPopupMenu);

        return view;
    }


    // 팝업 메뉴를 표시하고 팝업 메뉴 아이템 클릭에 따른 동작을 처리

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(requireContext(), view);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            // 팝업 메뉴 아이템 클릭 시 실행할 로직 추가
            switch (menuItem.getItemId()) {
                case R.id.menu_my_location:
                    Intent intent1 = new Intent(requireContext(), MyLocationActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.menu_hot_place:
                    Intent intent2 = new Intent(requireContext(), LocalSelectActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.menu_route:
                    Intent intent3 = new Intent(requireContext(), CalendarActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.menu_checklist:
                    Intent intent4 = new Intent(requireContext(), CheckListActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.menu_travel_log:
                    Intent intent5 = new Intent(requireContext(), PlanActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.menu_expense_graph:
                    break;
                case R.id.menu_mypage:
                    Intent intent7 = new Intent(requireContext(), MypageActivity.class);
                    startActivity(intent7);
                    break;
            }

            return true;
        });
        popup.show();
    }


    // 주소 검색 액티비티에서 결과를 처리하고 RecyclerView에 추가
    // 'startAddressSearch' 메서드는 주소 검색 액티비티를 시작
    private void startAddressSearch(int requestCode) {
        Intent intent = new Intent(requireActivity(), AddrResearchActivity.class);
        intent.putExtra("selectDate", daysDiff);
        intent.putExtra("requestCode", requestCode); // 각 버튼에 대한 요청 코드 추가
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode >= 1 && requestCode <= 5) {
                String address = data.getStringExtra("address");

                // 데이터 목록의 크기를 확인하고 최대 카운트를 초과하는 경우 항목을 제거합니다.
                ArrayList<String> currentDataList = getDataListByRequestCode(requestCode);
                AddressAdapter currentAdapter = getAdapterByRequestCode(requestCode);

                if (currentDataList.size() >= MAX_ADDRESS_COUNT) {
                    currentDataList.remove(0); // 가장 오래된 항목 제거
                    currentAdapter.notifyItemRemoved(0);
                }

                // 주소가 기존 목록에 있는지 확인하고 업데이트 또는 추가합니다.
                if (currentDataList.contains(address)) {
                    // 기존 목록에 주소가 있으면 업데이트
                    int index = currentDataList.indexOf(address);
                    currentDataList.set(index, address);
                    currentAdapter.notifyItemChanged(index);
                } else {
                    // 기존 목록에 주소가 없으면 추가
                    currentDataList.add(address);
                    currentAdapter.notifyItemInserted(currentDataList.size() - 1);
                }


                saveAddressToDatabase(address, getCategoryByRequestCode(requestCode));

            }
        }
    }

    private ArrayList<String> getDataListByRequestCode(int requestCode) {
        switch (requestCode) {

            case 1:
                return rv1DataList;
            case 2:
                return rv2DataList;
            case 3:
                return rv3DataList;
            case 4:
                return rv4DataList;
            case 5:
                return rv5DataList;
            default:
                return new ArrayList<>(); // 기본적으로 빈 목록 반환
        }
    }

    private AddressAdapter getAdapterByRequestCode(int requestCode) {
        switch (requestCode) {
            case 1:
                return rv1Adapter;
            case 2:
                return rv2Adapter;
            case 3:
                return rv3Adapter;
            case 4:
                return rv4Adapter;
            case 5:
                return rv5Adapter;
            default:
                return null;
        }
    }

    // 카테고리별 주소 목록을 가져오는 메소드
    private List<PlaceEntity> getPlacesByCategory(String category) {
        AppDatabase database = Room.databaseBuilder(requireContext(), AppDatabase.class, "places-db")
                .fallbackToDestructiveMigration()
                .build();
        // AppDatabase database = Room.databaseBuilder(requireContext(), AppDatabase.class, "places-db").build();
        return database.placeDao().getPlacesByCategory(category);
    }

    // 주소를 데이터베이스에 저장하는 메서드
    private void saveAddressToDatabase(String address, String category) {
        AsyncTask.execute(() -> {
            AppDatabase database = Room.databaseBuilder(requireContext(), AppDatabase.class, "places-db")
                    .fallbackToDestructiveMigration()
                    .build();
            // AppDatabase database = Room.databaseBuilder(requireContext(), AppDatabase.class, "places-db").build();
            // PlaceEntity 객체 생성 및 데이터 설정
            PlaceEntity place = new PlaceEntity();
            place.setAddress(address);
            place.setCategory(category);
            place.setPosition(position);  // 프래그먼트의 위치 정보 설정

            try {
                // 데이터베이스에 저장
                database.placeDao().insert(place);

                // 성공적으로 삭제되었음을 로그에 출력
                Log.d("Database", "Address saved: " + address + ", category:"+ category + ", position:" + position);

            } catch (Exception e) {
                // 삭제 중 오류가 발생했음을 로그에 출력
                Log.e("Database", "Error saving address: " + address, e);
            }
        });

    }

    // 각 요청 코드에 따라 카테고리를 반환하는 메서드
    private String getCategoryByRequestCode(int requestCode) {
        switch (requestCode) {
            case 1:
                return "음식점";
            case 2:
                return "카페";
            case 3:
                return "쇼핑";
            case 4:
                return "문화/관광";
            case 5:
                return "숙박";
            default:
                return "";
        }
    }


}