package com.example.seoulo1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

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

        rv1Adapter = new AddressAdapter(rv1DataList);
        rv2Adapter = new AddressAdapter(rv2DataList);
        rv3Adapter = new AddressAdapter(rv3DataList);
        rv4Adapter = new AddressAdapter(rv4DataList);
        rv5Adapter = new AddressAdapter(rv5DataList);


        // RecyclerView 및 관련 어댑터, 레이아웃 매니저를 설정
        rv1.setLayoutManager(rv1LayoutManager);
        rv1.setAdapter(rv1Adapter);

        rv2.setLayoutManager(rv2LayoutManager);
        rv2.setAdapter(rv2Adapter);

        rv3.setLayoutManager(rv3LayoutManager);
        rv3.setAdapter(rv3Adapter);

        rv4.setLayoutManager(rv4LayoutManager);
        rv4.setAdapter(rv4Adapter);

        rv5.setLayoutManager(rv5LayoutManager);
        rv5.setAdapter(rv5Adapter);


        // 버튼 클릭 이벤트를 처리하고 주소 검색 액티비티를 시작하는 버튼을 설정
        Button btn_AddrReserch_move = view.findViewById(R.id.btn_AddrReserch_move);
        Button btn_AddrReserch_move2 = view.findViewById(R.id.btn_AddrReserch_move2);
        Button btn_AddrReserch_move3 = view.findViewById(R.id.btn_AddrReserch_move3);
        Button btn_AddrReserch_move4 = view.findViewById(R.id.btn_AddrReserch_move4);
        Button btn_AddrReserch_move5 = view.findViewById(R.id.btn_AddrReserch_move5);
        Button Route_Recom = view.findViewById(R.id.Route_Recom);

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

        Route_Recom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 루트 추천 버튼 클릭 시 실행할 로직 추가
                Intent intent = new Intent(requireContext(), AddrSetActivity.class);
                startActivity(intent);
            }
        });



        // 나머지 버튼과 메뉴 버튼 관련 코드 추가
        like_btn = view.findViewById(R.id.like_btn);
        menu_btn = view.findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(this::showPopupMenu);

        return view;
    }


    // 'startAddressSearch' 메서드는 주소 검색 액티비티를 시작
    private void startAddressSearch(int requestCode) {
        Intent intent = new Intent(requireActivity(), AddrResearchActivity.class);
        intent.putExtra("selectDate", daysDiff);
        startActivityForResult(intent, requestCode);
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
                currentDataList.add(address);
                currentAdapter.notifyItemInserted(currentDataList.size() - 1);
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



}