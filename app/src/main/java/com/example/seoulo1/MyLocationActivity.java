package com.example.seoulo1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.SphericalUtil;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import noman.googleplaces.Place;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MyLocationActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        PlacesListener, GoogleMap.OnMarkerClickListener, myLocationAdapter.ListBtnClickListener {

    private GoogleMap mMap;
    static final String DB_NAME = "Check.db";
    private ImageButton like_btn, menu_btn,my_location_btn, list_location, button_sort;
    private ImageView like;
    private boolean isImage1 = true;

    ArrayList<Double> lat_list;
    ArrayList<Double> lng_list;
    ArrayList<Integer> distance_list;
    ArrayList<String> name_list, placeId_list, vicinity_list, pNum_list, open_now_list, rating_list;

    ArrayList<Marker> markers_list;

    String[] category_name_array={"맛집", "카페", "편의점","쇼핑","관광/문화"};
    String[][] category_value_array={{"restaurant", "bar", "meal_delivery", "meal_takeaway"}, {"cafe", "bakery"}, {"convenience_store"},{"department_store", "jewelry_store", "clothing_store", "liquor_store", "shoe_store", "shopping_mall"}, {"tourist_attraction","amusement_park", "park","museum", "art_gallery", "aquarium", "movie_theater", "stadium", "zoo", "movie_rental", "casino", "stadium", "city_hall"} };
    final List<LocationItem> locationItem = new ArrayList<>();

    private Button button_restaurant, button_cafe, button_cvstore, button_shopping, button_sights;
    private Marker currentMarker = null;

    public static final String TABLE_NAME = "Location";
    SQLiteDatabase db;
    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용됩니다.
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    boolean needRequest = false;
    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소


    Location mCurrentLocation;
    LatLng currentPosition;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;

    myLocationAdapter adapter;
    private View mLayout;  // Snackbar 사용하기 위해서는 View가 필요합니다.
    // (참고로 Toast에서는 Context가 필요했습니다.)

    List<Marker> previous_marker = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        final ListView listView = findViewById(R.id.listView);
        adapter=new myLocationAdapter(this, R.layout.list_view_items, locationItem, this, listView);
        listView.setAdapter(adapter);
        db=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_my_location);
        //assert mapFragment != null;
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mLayout = findViewById(R.id.layout_my_location);


        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(100)
                .build();

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        previous_marker = new ArrayList<>();
        lat_list= new ArrayList<>();
        lng_list= new ArrayList<>();
        name_list= new ArrayList<>();
        vicinity_list= new ArrayList<>();
        distance_list= new ArrayList<>();
        markers_list=new ArrayList<>();
        placeId_list=new ArrayList<>();
        pNum_list = new ArrayList<>();
        open_now_list = new ArrayList<>();
        rating_list = new ArrayList<>();


        button_restaurant = findViewById(R.id.button_restaurant);
        button_restaurant.setOnClickListener(v -> {
            button_restaurant.setSelected(true);
            button_cafe.setSelected(false);
            button_cvstore.setSelected(false);
            button_shopping.setSelected(false);
            button_sights.setSelected(false);
            //showPlaceInformation_restaurant(currentPosition);
            String type[][] = new String[5][5000];
            mMap.clear();//지도 클리어
            // 사용자가 선택한 항목 인덱스번째의 type 값을 가져온다.
            int j, i=0;
            for(j=0; j< category_value_array[i].length;j++ ){
                type[0][j]=category_value_array[i][j];
            }
            // 주변 정보를 가져온다
            for (int k=0; k<j; k++) {
                getNearbyPlace(type[0][k]);

            }
            lat_list.clear();
            lng_list.clear();
            name_list.clear();
            vicinity_list.clear();
            distance_list.clear();
            placeId_list.clear();
            pNum_list.clear();
            locationItem.clear();
            open_now_list.clear();
            rating_list.clear();

        });

        button_cafe = findViewById(R.id.button_cafe);
        button_cafe.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(true);
            button_cvstore.setSelected(false);
            button_shopping.setSelected(false);
            button_sights.setSelected(false);
            //showPlaceInformation_cafe(currentPosition);
            String type[][] = new String[5][5000];
            mMap.clear();//지도 클리어
            // 사용자가 선택한 항목 인덱스번째의 type 값을 가져온다.
            int j, i=1;
            for(j=0; j< category_value_array[i].length;j++ ){
                type[0][j]=category_value_array[i][j];
            }
            // 주변 정보를 가져온다
            for (int k=0; k<j; k++) {
                getNearbyPlace(type[0][k]);

            }
            lat_list.clear();
            lng_list.clear();
            placeId_list.clear();
            name_list.clear();
            vicinity_list.clear();
            distance_list.clear();
            pNum_list.clear();
            open_now_list.clear();
            rating_list.clear();
            locationItem.clear();
        });

        button_cvstore = findViewById(R.id.button_cvstore);
        button_cvstore.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(false);
            button_cvstore.setSelected(true);
            button_shopping.setSelected(false);
            button_sights.setSelected(false);
            //showPlaceInformation_cvstore(currentPosition);
            String type[][] = new String[5][5000];
            mMap.clear();//지도 클리어
            // 사용자가 선택한 항목 인덱스번째의 type 값을 가져온다.
            int j, i=2;
            for(j=0; j< category_value_array[i].length;j++ ){
                type[0][j]=category_value_array[i][j];
            }
            // 주변 정보를 가져온다
            for (int k=0; k<j; k++) {
                getNearbyPlace(type[0][k]);

            }
            lat_list.clear();
            placeId_list.clear();
            lng_list.clear();
            name_list.clear();
            vicinity_list.clear();
            distance_list.clear();
            pNum_list.clear();
            locationItem.clear();
            open_now_list.clear();
            rating_list.clear();

        });

        button_shopping = findViewById(R.id.button_shopping);
        button_shopping.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(false);
            button_cvstore.setSelected(false);
            button_shopping.setSelected(true);
            button_sights.setSelected(false);
            //showPlaceInformation_shopping(currentPosition);
            String type[][] = new String[5][5000];
            mMap.clear();//지도 클리어
            // 사용자가 선택한 항목 인덱스번째의 type 값을 가져온다.
            int j, i=3;
            for(j=0; j< category_value_array[i].length;j++ ){
                type[0][j]=category_value_array[i][j];
            }
            // 주변 정보를 가져온다
            for (int k=0; k<j; k++) {
                getNearbyPlace(type[0][k]);

            }
            lat_list.clear();
            lng_list.clear();
            placeId_list.clear();
            name_list.clear();
            vicinity_list.clear();
            distance_list.clear();
            pNum_list.clear();
            locationItem.clear();
            open_now_list.clear();
            rating_list.clear();

        });

        button_sights = findViewById(R.id.button_sights);
        button_sights.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(false);
            button_cvstore.setSelected(false);
            button_shopping.setSelected(false);
            button_sights.setSelected(true);
            //showPlaceInformation_sights(currentPosition);
            String type[][] = new String[5][5000];
            mMap.clear();//지도 클리어
            // 사용자가 선택한 항목 인덱스번째의 type 값을 가져온다.
            int j, i=4;
            for(j=0; j< category_value_array[i].length;j++ ){
                type[0][j]=category_value_array[i][j];
            }
            // 주변 정보를 가져온다
            for (int k=0; k<j; k++) {
                getNearbyPlace(type[0][k]);

            }
            lat_list.clear();
            lng_list.clear();
            placeId_list.clear();
            name_list.clear();
            vicinity_list.clear();
            distance_list.clear();
            pNum_list.clear();
            locationItem.clear();open_now_list.clear();
            rating_list.clear();

        });


        like_btn = findViewById(R.id.like_btn);
        like_btn.setOnClickListener(view ->{
            Intent intent5 = new Intent(MyLocationActivity.this, LikeActivity.class);
            startActivity(intent5);
        });

        list_location = findViewById(R.id.list_location);
        list_location.setOnClickListener(v ->{
            SlidingUpPanelLayout layout_my_location;
            layout_my_location=findViewById(R.id.layout_my_location);
            if(layout_my_location.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED){
                layout_my_location.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }else{
                layout_my_location.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);}
            //showCategoryList();
        });

        button_sort = findViewById(R.id.button_sort);
        button_sort.setOnClickListener(view->{
            PopupMenu popup= new PopupMenu(MyLocationActivity.this, view); //두 번째 파라미터가 팝업메뉴가 붙을 뷰
            //PopupMenu popup= new PopupMenu(MainActivity.this, btn2); //첫번째 버튼을 눌렀지만 팝업메뉴는 btn2에 붙어서 나타남
            getMenuInflater().inflate(R.menu.popup_sort, popup.getMenu());

            //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
            popup.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()){
                    case R.id.menu_rating:
                        Collections.sort(locationItem, Collections.reverseOrder(Comparator.comparingDouble(o -> {
                            if (o.rating != null && !o.rating.isEmpty()) {
                                return Double.parseDouble(o.rating);
                            } else {
                                return -1;
                            }
                        })));
                        break;

                    case R.id.menu_distance:
                        Collections.sort(locationItem, Comparator.comparingInt(o -> o.distance));
                        break;
                }
                //final ListView listView = findViewById(R.id.listView);
                showMarker();
                final myLocationAdapter adapter=new myLocationAdapter(this,0, locationItem,this, listView);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                //return false;
                return false;
            });
            popup.show();
        });

        menu_btn = findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(view -> {

            //PopupMenu 객체 생성
            PopupMenu popup= new PopupMenu(MyLocationActivity.this, view); //두 번째 파라미터가 팝업메뉴가 붙을 뷰
            //PopupMenu popup= new PopupMenu(MainActivity.this, btn2); //첫번째 버튼을 눌렀지만 팝업메뉴는 btn2에 붙어서 나타남
            getMenuInflater().inflate(R.menu.popup, popup.getMenu());

            //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
            popup.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()){
                    case R.id.menu_my_location:
                        Intent intent1 = new Intent(MyLocationActivity.this, MyLocationActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.menu_hot_place:
                        Intent intent2 = new Intent(MyLocationActivity.this, LocalSelectActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.menu_route:
                        Intent intent3 = new Intent(MyLocationActivity.this, CalendarActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menu_checklist:
                        Intent intent4 = new Intent(MyLocationActivity.this, CheckListActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.menu_travel_log:
                        Intent intent5 = new Intent(MyLocationActivity.this, PlanActivity.class);
                        startActivity(intent5);
                        break;
                    case R.id.menu_expense_graph:
                        Intent intent6 = new Intent(MyLocationActivity.this, GraphActivity.class);
                        startActivity(intent6);
                        break;
                    case R.id.menu_mypage:
                        Intent intent7 = new Intent(MyLocationActivity.this, MypageActivity.class);
                        startActivity(intent7);
                        break;
                }

                return false;
            });
            popup.show();
        });

    }

    //주변 정보 가져오기
    public void getNearbyPlace(String type_keyword){
        NetworkThread thread=new NetworkThread(type_keyword);
        thread.start();

    }
    class NetworkThread extends Thread{

        String type_keyword;
        public NetworkThread(String type_keyword){
            this.type_keyword=type_keyword;
        }
        @Override
        public void run() {
            try{
                //데이터를 담아놓을 리스트를 초기화한다.
                //밑에 위도 경도에 원래 이거 넣기 mCurrentLocation.getLatitude()
                // 접속할 페이지 주소
                String site="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+37.56+","
                        +126.97
                        +"&radius=2000&sensor=false&language=ko"
                        +"&key=AIzaSyC4KSNnjRcOOrDmEnkPbiRsBJ8X2czcesY";
                if(type_keyword!=null && type_keyword.equals("all")==false){
                    site+="&types="+type_keyword;
                    Log.d(TAG, " 장소 항목 누름 !!!!!!!!!! if 들어옴"+ type_keyword);

                }
                // 접속
                URL url=new URL(site);
                Log.d(TAG, site  +" 여기 site 주소");
                URLConnection conn=url.openConnection();
                // 스트림 추출
                InputStream is=conn.getInputStream();
                InputStreamReader isr =new InputStreamReader(is,"utf-8");
                BufferedReader br=new BufferedReader(isr);
                String str=null;
                StringBuffer buf=new StringBuffer();
                // 읽어온다
                do{
                    str=br.readLine();
                    if(str!=null){
                        buf.append(str);
                    }
                }while(str!=null);
                String rec_data=buf.toString();
                // JSON 데이터 분석
                JSONObject root=new JSONObject(rec_data);
                //status 값을 추출한다.
                String status=root.getString("status");
                // 가져온 값이 있을 경우에 지도에 표시한다.
                if(status.equals("OK")){
                    //results 배열을 가져온다
                    JSONArray results=root.getJSONArray("results");
                    // 개수만큼 반복한다.
                    for(int i=0; i<results.length() ; i++){
                        // 객체를 추출한다.(장소하나의 정보)
                        JSONObject obj1=results.getJSONObject(i);
                        // 위도 경도 추출
                        JSONObject geometry=obj1.getJSONObject("geometry");
                        JSONObject location=geometry.getJSONObject("location");
                        double lat=location.getDouble("lat");
                        double lng=location.getDouble("lng");
                        // 장소 이름 추출
                        String name=obj1.getString("name");
                        // 대략적인 주소 추출
                        String vicinity=obj1.getString("vicinity");
                        currentPosition= new LatLng(37.56, 126.97);   //현재 위치로 임의 설정, gps 쓸 시 주석처리
                        LatLng obj1Position = new LatLng(lat, lng);
                        double distance = SphericalUtil.computeDistanceBetween(currentPosition, obj1Position);
                        String placeId = obj1.getString("place_id");
                        Log.d(TAG, placeId + "place id %%%%%%%%");
                        // 데이터를 담는다.
//                        lat_list.add(lat);
//                        lng_list.add(lng);
//                        name_list.add(name);
//                        vicinity_list.add(vicinity);
//                        distance_list.add((int)distance);
                        placeId_list.add(placeId);
                        getPlaceDetails(placeId);
                       // locationItem.add(new LocationItem(placeId, name, type_keyword,vicinity, (int) distance));
                        //Log.d(TAG, "typekeyword !!!!!!!!!!!!!!!!!!======="+ type_keyword);

                    }
//                    for(int i=0; i<results.length() ; i++){
//                        getPlaceDetails(placeId_list.get(i));
//                    }
                    //showMarker();

                }
                else{
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            //Toast.makeText(getApplicationContext(),"가져온 데이터가 없습니다.",Toast.LENGTH_LONG).show();
                        }
                    }, 0);
                }

            }catch (Exception e){e.printStackTrace();}
        }



        private void getPlaceDetails(String placeId) throws IOException, JSONException {
            String site2 = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeId + "&key=" + "AIzaSyC4KSNnjRcOOrDmEnkPbiRsBJ8X2czcesY";
            Log.d(TAG, site2  +" 여기 site 주소");
            URL url2=new URL(site2);
            URLConnection conn=url2.openConnection();
            // 스트림 추출
            InputStream is=conn.getInputStream();
            InputStreamReader isr =new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            String str=null;
            StringBuffer buf=new StringBuffer();
            // 읽어온다
            do{
                str=br.readLine();
                if(str!=null){
                    buf.append(str);
                }
            }while(str!=null);
            String rec_data=buf.toString();
            Log.d(TAG, "111111111111111 getPlaceDetails: " + rec_data);
            // JSON 데이터 분석
            JSONObject root=new JSONObject(rec_data);
            //status 값을 추출한다.
            String status=root.getString("status");
            Log.d(TAG, "2222222 getPlaceDetails: " + status);

            // 가져온 값이 있을 경우에 지도에 표시한다.
            if(status.equals("OK")){
                //results 배열을 가져온다
                JSONObject result=root.getJSONObject("result");
                double lat = result.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                double lng = result.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                String name = result.getString("name");
                String vicinity = result.getString("vicinity");
                String pNum = result.optString("formatted_phone_number", ""); // formatted_phone_number가 없을 경우 빈 문자열 반환
                String open_now;
                if (result.has("current_opening_hours")) {
                    JSONObject openingHours = result.getJSONObject("current_opening_hours");
                    if (openingHours.has("open_now")) {
                        if (openingHours.getBoolean("open_now")) {
                            open_now = "영업 중";
                        } else {
                            open_now = "영업 종료";
                        }
                    } else {
                        open_now = ""; // "open_now" 키가 없는 경우
                    }
                } else {
                    open_now = ""; // "current_opening_hours" 키가 없는 경우
                }
                String rating;
                if (result.has("rating")) {
                    rating = String.valueOf(result.getDouble("rating"));
                } else {
                    rating = ""; // "rating" 키가 없는 경우
                }
                currentPosition= new LatLng(37.56, 126.97);   //현재 위치로 임의 설정, gps 쓸 시 주석처리
                LatLng obj1Position = new LatLng(lat, lng);
                double distance = SphericalUtil.computeDistanceBetween(currentPosition, obj1Position);
                    // 데이터를 담는다.
                    lat_list.add(lat);
                    lng_list.add(lng);
                    name_list.add(name);
                    vicinity_list.add(vicinity);
                    distance_list.add((int)distance);
                    open_now_list.add(open_now);
                    rating_list.add(rating);
                    pNum_list.add(pNum);
                    locationItem.add(new LocationItem(placeId, name, type_keyword,vicinity, (int) distance, pNum, open_now, rating, lat, lng, false));
                    Log.d(TAG, "item 추가 !!!!!!!!!!!!!!!!!!=======" + name);

                //}
                showMarker();

            }
            else{
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplicationContext(),"가져온 데이터가 없습니다.",Toast.LENGTH_LONG).show();
                    }
                }, 0);
            }

        }
        //catch (Exception e){e.printStackTrace();}

    }

       public void showMarker(){
        runOnUiThread(() -> {
            // 지도에 마커를 표시한다.
            // 지도에 표시되어있는 마커를 모두 제거한다.
//            for(Marker marker : markers_list){
//                marker.remove();
//            }
//            markers_list.clear();
            final ListView listView = findViewById(R.id.listView);
            //Collections.sort(locationItem, Comparator.comparingInt(o -> o.distance));
            final myLocationAdapter adapter=new myLocationAdapter(this,0, locationItem,this, listView);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            // 가져온 데
            // 이터의 수 만큼 마커 객체를 만들어 표시한다.
            for(int i= 0 ; i< lat_list.size() ; i++){
                // 값 추출
                double lat= lat_list.get(i);
                double lng=lng_list.get(i);
                String name=name_list.get(i);
                String vicinity=vicinity_list.get(i);
                int distance=distance_list.get(i);
                String placeId = placeId_list.get(i);


                // 생성할 마커의 정보를 가지고 있는 객체를 생성
                MarkerOptions options=new MarkerOptions();
                // 위치설정
                LatLng pos=new LatLng(lat,lng);
                options.position(pos);
                // 말풍선이 표시될 값 설정
                options.title(name);
                options.snippet(vicinity+ "  여기서 "+distance+"m");
                @SuppressLint("UseCompatLoadingForDrawables")
                //BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.mapmarkerblue);
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.mapmarkerblue);
                if (drawable instanceof BitmapDrawable) {
                    Bitmap b = ((BitmapDrawable) drawable).getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 65, 90, false);
                    options.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    Marker marker=mMap.addMarker(options);
                    markers_list.add(marker);
                    // 이제 bitmap을 사용할 수 있습니다.
                } else {
                    // BitmapDrawable이 아닌 경우에 대한 처리
                    Log.d(TAG, "showMarker: errrrrrrrrrrrrrrrrrrrrrrrrrror");
                }

            }
        });
    }


    @Override
    public void onMapReady(@NonNull final GoogleMap googleMap) {
        Log.d(TAG, "onMapReady :");

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);



        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            startLocationUpdates(); // 3. 위치 업데이트 시작


        }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
                        Snackbar.LENGTH_INDEFINITE).setAction("확인", view -> {

                    // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                    ActivityCompat.requestPermissions( MyLocationActivity.this, REQUIRED_PERMISSIONS,
                            PERMISSIONS_REQUEST_CODE);
                }).show();


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 현재 오동작을 해서 주석처리
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setOnMapClickListener(latLng -> Log.d( TAG, "onMapClick :"));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //ImageButton distance_btn = findViewById(R.id.distance_btn);
        currentPosition= new LatLng(37.56, 126.97);   //현재 위치로 임의 설정, gps 쓸 시 주석처리
        double distance = SphericalUtil.computeDistanceBetween(currentPosition, marker.getPosition());
//        distance_btn.setOnClickListener(v -> {
//            Toast.makeText(MyLocationActivity.this, (int) distance + "m 남음", Toast.LENGTH_LONG).show();
//            Log.d(TAG, "onMarkerClick: 출력완료");
//
//        });
        return false;
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition
                        = new LatLng(location.getLatitude(), location.getLongitude());


                String markerTitle = getCurrentAddress(currentPosition);
                String markerSnippet = "위도:" + location.getLatitude()
                        + " 경도:" + location.getLongitude();

                Log.d(TAG, "onLocationResult : " + markerSnippet);


                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(location, markerTitle, markerSnippet);

                mCurrentLocation = location;
            }


        }

    };



    private void startLocationUpdates() {

        if (!checkLocationServicesStatus()) {

            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);



            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }


            Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");

            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

            if (checkPermission()){
                Log.d(TAG, "파란점 찍기");
                mMap.setMyLocationEnabled(true);}

        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");

        if (checkPermission()) {

            Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);

            if (mMap!=null)
                mMap.setMyLocationEnabled(true);

        }


    }


    @Override
    protected void onStop() {

        super.onStop();

        if (mFusedLocationClient != null) {

            Log.d(TAG, "onStop : call stopLocationUpdates");
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }




    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            //addresses= new LatLng(37.56, 126.97);
            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
            Log.d(TAG, "getCurrentAddress:!!!!!!");

        } catch (IOException ioException) {
            //네트워크 문제
            //Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0);
        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {


        if (currentMarker != null) currentMarker.remove();


        //LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude()); //현재 위치 구하기 코든데 지금 안됨, test 해볼려면 밑에 코드 주석처리하고 이코드 사용
        LatLng currentLatLng= new LatLng(37.56, 126.97);   //임의로 위치 설정
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(R.drawable.marker1));

        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
        mMap.moveCamera(cameraUpdate);

    }


    public void setDefaultLocation() {
        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";


        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mMap.moveCamera(cameraUpdate);

    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    private boolean checkPermission() {

        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED;

    }



    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
                startLocationUpdates();
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {


                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", view -> finish()).show();

                } else {


                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
                            Snackbar.LENGTH_INDEFINITE).setAction("확인", view -> finish()).show();
                }
            }

        }
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyLocationActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", (dialog, id) -> {
            Intent callGPSSettingIntent
                    = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            setResult(GPS_ENABLE_REQUEST_CODE,callGPSSettingIntent);

        });

        builder.setNegativeButton("취소", (dialog, id) -> dialog.cancel());
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_ENABLE_REQUEST_CODE) {//사용자가 GPS 활성 시켰는지 검사
            if (checkLocationServicesStatus()) {
                if (checkLocationServicesStatus()) {

                    Log.d(TAG, "onActivityResult : GPS 활성화 되있음");

                    needRequest = true;
                }
            }
        }
    }

    @Override
    public void onPlacesFailure(PlacesException e) {

    }

    @Override
    public void onPlacesStart() {

    }
    //지금 안쓰는 클래스
    @SuppressLint("ResourceType")
    @Override
    public void onPlacesSuccess(final List<Place> places) {
        runOnUiThread(() -> {
            for (Place place : places) {

                LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());
                String markerSnippet = getCurrentAddress(latLng);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(place.getName());
                markerOptions.snippet(markerSnippet);
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.mapmarkerblue);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 65, 90, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                Marker item = mMap.addMarker(markerOptions);
                previous_marker.add(item);

            }

            //중복 마커 제거
            HashSet<Marker> hashSet = new HashSet<>(previous_marker);
            previous_marker.clear();
            previous_marker.addAll(hashSet);

        });

    }

    @Override
    public void onPlacesFinished() {

    }

    @SuppressLint({"NonConstantResourceId", "Range"})
    @Override
    public void onListButtonClick(int position, int resourceid) {
        Log.d(TAG, position + " $$$$$$ 하트 like 누름 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        switch (resourceid) {
            case R.id.like -> {
                LocationItem selectedLocation = locationItem.get(position);

                Log.d(TAG, position + " $$$$$$ 하트 들어옴 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                // LikeActivity로 전달할 데이터 설정
                List<LocationItem> likedLocations = new ArrayList<>();
                likedLocations.add(selectedLocation);
                boolean newStatus = (selectedLocation.getStatus() == false) ? true : false;


                DBHelper dbHelper = new DBHelper(this);
                db = dbHelper.getWritableDatabase();
                selectedLocation.setStatus(newStatus);
                if (newStatus==false) {
                    likedLocations.remove(selectedLocation);
                    dbHelper.deleteFavorite(selectedLocation.getLName()); // DB에서 삭제
                } else {
                    dbHelper.insertFavorite(
                            selectedLocation.getCategory_name(),
                            selectedLocation.getLName(),
                            selectedLocation.getPlaceId(),
                            selectedLocation.getpNum(),
                            selectedLocation.getDistance(),
                            selectedLocation.getVicinity(),
                            selectedLocation.getOpen_now(),
                            selectedLocation.getRating(),
                            selectedLocation.getLat(),
                            selectedLocation.getLng(), newStatus
                    );
                }
                Log.d(TAG, " 즐겨찾기 장소 저장 또는 삭제: " + selectedLocation);
                final ListView listView = findViewById(R.id.listView);
                final myLocationAdapter adapter=new myLocationAdapter(this,0, locationItem,this, listView);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                // LikeActivity 시작
                //Intent likeIntent = new Intent(this, LikeActivity.class);
                //likeIntent.putExtra("likedLocations", likedLocations);
                //startActivity(likeIntent);
                break;
            }
        }
    }


}