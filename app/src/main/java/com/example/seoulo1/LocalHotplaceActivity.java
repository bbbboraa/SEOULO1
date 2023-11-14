package com.example.seoulo1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LocalHotplaceActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PlacesClient placesClient;
    private String apiKey = "AIzaSyC4KSNnjRcOOrDmEnkPbiRsBJ8X2czcesY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_hotplace);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_local_location);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "SupportMapFragment가 null입니다.", Toast.LENGTH_SHORT).show();
        }

        // 초기화
        Places.initialize(getApplicationContext(), apiKey);
        placesClient = Places.createClient(this);


        Button restaurantButton = findViewById(R.id.button_restaurant);
        Button cafeButton = findViewById(R.id.button_cafe);
        Button shoppingButton = findViewById(R.id.button_shopping);
        Button tourButton = findViewById(R.id.button_tour);

        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayListViewContents("Gangseo/gangseo_food.txt");
            }
        });

        cafeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayListViewContents("Gangseo/gangseo_cafe.txt");
            }
        });

        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayListViewContents("Gangseo/gangseo_shop.txt");
            }
        });

        tourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayListViewContents("Gangseo/gangseo_tour.txt");
            }
        });

        // 권한 체크 및 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            // 권한이 이미 허용된 경우 파일 읽어오기를 진행합니다.
            addMarkersFromFile("Gangseo/gangseo_food.txt");
            addMarkersFromFile("Gangseo/gangseo_cafe.txt");
            addMarkersFromFile("Gangseo/gangseo_shop.txt");
            addMarkersFromFile("Gangseo/gangseo_tour.txt");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // 강서구의 중심 좌표를 정의합니다.
        LatLng gangseoGuCenter = new LatLng(37.546772, 126.829423); // 강서구 중심 좌표
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(gangseoGuCenter, 13); // 줌 레벨을 조절하여 원하는 확대 수준 설정
        mMap.animateCamera(cameraUpdate);

        // 권한 체크 및 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            addMarkersFromFile("Gangseo/gangseo_food.txt");
            addMarkersFromFile("Gangseo/gangseo_cafe.txt");
            addMarkersFromFile("Gangseo/gangseo_shop.txt");
            addMarkersFromFile("Gangseo/gangseo_tour.txt");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 로그: 권한 요청 결과를 출력합니다.
        Log.d("PermissionResult", "Request Code: " + requestCode);

        // 권한 요청 결과 처리
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PermissionResult", "파일 읽기를 시작합니다.");
                // 권한이 허용된 경우 파일 읽어오기
                addMarkersFromFile("Gangseo/gangseo_food.txt");
                addMarkersFromFile("Gangseo/gangseo_cafe.txt");
                addMarkersFromFile("Gangseo/gangseo_shop.txt");
                addMarkersFromFile("Gangseo/gangseo_tour.txt");
            } else {
                // 권한이 거부된 경우 메시지 표시
                Log.d("PermissionResult", "파일 읽기 권한이 필요합니다. 로그 출력.");
                Toast.makeText(this, "파일 읽기 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addMarkersFromFile(String fileName) {
        try {
            InputStream inputStream = getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //학교getLatLngFromAddress(line, fileName);
            }

            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getLatLngFromAddress 메소드 내부에서 마커를 추가할 때 마커에 정보를 추가합니다.
//    private void getLatLngFromAddress(String address, String fileName) {
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS);
//
//        FetchPlaceRequest request = FetchPlaceRequest.builder(address, placeFields).build();
//        placesClient.fetchPlace(request)
//                .addOnSuccessListener((response) -> {
//                    Place place = response.getPlace();
//                    LatLng location = place.getLatLng();
//
//                    if (location != null) {
//                        // 서울 강서구에 속하는지 확인
//                        if (isInGangseoGu(location)) {
//                            // 마커 추가
//                            MarkerOptions markerOptions = new MarkerOptions()
//                                    .position(location)
//                                    .title(place.getName()) // 장소명을 마커의 제목으로 설정
//                                    .snippet(place.getAddress()); // 주소를 마커의 부제목으로 설정
//                            mMap.addMarker(markerOptions);
//
//                            // 카메라 이동
//                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 15);
//                            mMap.animateCamera(cameraUpdate);
//                        }
//                    }
//                })
//                .addOnFailureListener((exception) -> {
//                    if (exception instanceof ApiException) {
//                        ApiException apiException = (ApiException) exception;
//                        int statusCode = apiException.getStatusCode();
//                        Log.e("Geocoding", "Place not found: " + exception.getMessage());
//                        Toast.makeText(this, "장소를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    private boolean isInGangseoGu(LatLng location) {
        // 강서구의 경계 좌표를 정의합니다. 예를 들어 강서구의 서쪽 경계 좌표와 동쪽 경계 좌표를 사용합니다.
        LatLng gangseoGuSouthWest = new LatLng(37.481553, 126.764295); // 강서구 서쪽 경계 좌표
        LatLng gangseoGuNorthEast = new LatLng(37.609634, 126.901649); // 강서구 동쪽 경계 좌표

        // 해당 좌표가 강서구에 속하는지 확인합니다.
        return location.latitude >= gangseoGuSouthWest.latitude &&
                location.latitude <= gangseoGuNorthEast.latitude &&
                location.longitude >= gangseoGuSouthWest.longitude &&
                location.longitude <= gangseoGuNorthEast.longitude;
    }


    private void displayListViewContents(String fileName) {
        try {
            InputStream inputStream = getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            List<String> placesList = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                placesList.add(line);
            }

            bufferedReader.close();
            inputStream.close();

            ListView listView = findViewById(R.id.listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placesList);
            listView.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
