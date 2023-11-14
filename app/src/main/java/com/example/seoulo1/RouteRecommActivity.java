package com.example.seoulo1;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RouteRecommActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapView mapView;
    private AppDatabase database;

    private int position;  // 날짜에 해당하는 위치

    // 액티비티가 생성될 때 호출되는 메서드. MapView 및 Room 데이터베이스 초기화.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_recomm);

        // 날짜 위치 정보 가져오기
        if (getIntent().getExtras() != null) {
            position = getIntent().getIntExtra("position", 0);
        }

        // MapView 초기화
        mapView = findViewById(R.id.Route_mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Room 데이터베이스 초기화
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "places-db")
                .fallbackToDestructiveMigration()
                .build();
    }

    // GoogleMap이 사용 가능해지면 호출되는 메서드. loadMarkersForDates를 호출하여 날짜별로 구분된 장소 정보를 지도에 표시
    @Override
    public void onMapReady(GoogleMap map) {
        // GoogleMap이 사용 가능해지면 호출되는 메서드
        googleMap = map;

        // 서울의 위도와 경도
        LatLng seoulLatLng = new LatLng(37.5665, 126.9780);

        // 지도를 서울로 이동 및 초기 줌 레벨 설정
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoulLatLng, 10.0f));

        // 날짜별로 구분된 장소 정보를 가져와 지도에 마커 추가
        loadMarkersForDates(position);
    }


    // 날짜별로 구분된 장소 정보를 가져와 지도에 마커를 추가하는 메서드. 이전에 추가된 마커를 모두 지우고 새로운 마커를 추가
    private void loadMarkersForDates(int position) {
        // 이전에 추가된 마커를 모두 지우고 새로운 마커를 추가
        googleMap.clear();

        // 해당 position에 대한 장소 정보 가져오기
        AsyncTask.execute(() -> {
            List<PlaceEntity> places = database.placeDao().getPlacesByPosition(position);

            // UI 업데이트를 위해 메인 스레드에서 실행
            runOnUiThread(() -> {
                for (PlaceEntity place : places) {
                    // 주소를 LatLng로 변환
                    LatLng location = getLocationFromAddress(this, place.getAddress());

                    // 마커 추가
                    if (location != null) {
                        googleMap.addMarker(new MarkerOptions().position(location).title(place.getAddress()));
                    }
                }

                // 지도의 중심을 첫 번째 마커 위치로 이동
                if (!places.isEmpty()) {
                    LatLng firstMarkerLocation = getLocationFromAddress(this, places.get(0).getAddress());
                    if (firstMarkerLocation != null) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(firstMarkerLocation));
                    }
                }
            });
        });
    }

    // 주소를 LatLng로 변환하는 메서드. Geocoder를 사용하여 주소를 위도와 경도로 변환
    private LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(strAddress, 1);

            if (addresses != null && addresses.size() > 0) {
                // 주소에서 위도와 경도 추출
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 추가로 로깅이나 사용자에게 알림을 보낼 수 있습니다.
        }

        return null;
    }

    // onResume, onPause, onDestroy, onLowMemory: 액티비티 생명 주기에 따라 MapView의 상태를 관리하는 메서드
    @Override
    protected void onResume() {
        // 액티비티가 화면에 표시될 때 호출되는 메서드
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        // 액티비티가 일시 중지될 때 호출되는 메서드
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        // 액티비티가 종료될 때 호출되는 메서드
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        // 시스템이 낮은 메모리 상태에 진입할 때 호출되는 메서드
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
