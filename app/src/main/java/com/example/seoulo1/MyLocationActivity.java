package com.example.seoulo1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import noman.googleplaces.NRPlaces;
import noman.googleplaces.Place;
import noman.googleplaces.PlaceType;
import noman.googleplaces.PlacesException;
import noman.googleplaces.PlacesListener;

public class MyLocationActivity extends AppCompatActivity  implements
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        PlacesListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ImageButton like_btn, menu_btn,my_location_btn;
    private Button button_restaurant, button_cafe, button_cvstore, button_shopping, button_sights;
    private Marker currentMarker = null;

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


    private View mLayout;  // Snackbar 사용하기 위해서는 View가 필요합니다.
    // (참고로 Toast에서는 Context가 필요했습니다.)

    List<Marker> previous_marker = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

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

        button_restaurant = findViewById(R.id.button_restaurant);
        button_restaurant.setOnClickListener(v -> {
            button_restaurant.setSelected(true);
            button_cafe.setSelected(false);
            button_cvstore.setSelected(false);
            button_shopping.setSelected(false);
            button_sights.setSelected(false);
            showPlaceInformation_restaurant(currentPosition);
        });

        button_cafe = findViewById(R.id.button_cafe);
        button_cafe.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(true);
            button_cvstore.setSelected(false);
            button_shopping.setSelected(false);
            button_sights.setSelected(false);
            showPlaceInformation_cafe(currentPosition);
        });

        button_cvstore = findViewById(R.id.button_cvstore);
        button_cvstore.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(false);
            button_cvstore.setSelected(true);
            button_shopping.setSelected(false);
            button_sights.setSelected(false);
            showPlaceInformation_cvstore(currentPosition);
        });

        button_shopping = findViewById(R.id.button_shopping);
        button_shopping.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(false);
            button_cvstore.setSelected(false);
            button_shopping.setSelected(true);
            button_sights.setSelected(false);
            showPlaceInformation_shopping(currentPosition);
        });

        button_sights = findViewById(R.id.button_sights);
        button_sights.setOnClickListener(v -> {
            button_restaurant.setSelected(false);
            button_cafe.setSelected(false);
            button_cvstore.setSelected(false);
            button_shopping.setSelected(false);
            button_sights.setSelected(true);
            showPlaceInformation_sights(currentPosition);
        });


        like_btn = findViewById(R.id.like_btn);
        my_location_btn = findViewById(R.id.my_location_btn);
        my_location_btn.setOnClickListener(v -> {
            Intent intent1 = new Intent(MyLocationActivity.this, MyLocationActivity.class);
            intent1.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent1);
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
                        break;
                    case R.id.menu_travel_log:
                        break;
                    case R.id.menu_expense_graph:
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
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setOnMapClickListener(latLng -> Log.d( TAG, "onMapClick :"));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        ImageButton distance_btn = findViewById(R.id.distance_btn);
        currentPosition= new LatLng(37.56, 126.97);   //현재 위치로 임의 설정, gps 쓸 시 주석처리
        double distance = SphericalUtil.computeDistanceBetween(currentPosition, marker.getPosition());
        distance_btn.setOnClickListener(v -> {
            Toast.makeText(MyLocationActivity.this, (int) distance + "m 남음", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onMarkerClick: 출력완료");

        });
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
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
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

    @SuppressLint("ResourceType")
    @Override
    public void onPlacesSuccess(final List<Place> places) {
        runOnUiThread(() -> {
            for (Place place : places) {

                LatLng latLng
                        = new LatLng(place.getLatitude()
                        , place.getLongitude());


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
    public void showPlaceInformation_restaurant(LatLng location) {
        mMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(MyLocationActivity.this)
                .key("AIzaSyDdlA0zHcNZ4wC1_DA6k3hg0_2tG91JzX8")
                //.latlng(location.latitude, location.longitude)//현재 위치
                .latlng(37.56, 126.97)   //임의로 위치 설정
                .radius(2000) //2km 내에서 검색
                .type(PlaceType.RESTAURANT) //음식점
                .type(PlaceType.BAR)
                .type(PlaceType.MEAL_DELIVERY)
                .type(PlaceType.MEAL_TAKEAWAY)
                .build()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void showPlaceInformation_cafe(LatLng location) {
        mMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(MyLocationActivity.this)
                .key("AIzaSyDdlA0zHcNZ4wC1_DA6k3hg0_2tG91JzX8")
                //.latlng(location.latitude, location.longitude)//현재 위치
                .latlng(37.56, 126.97)   //임의로 위치 설정
                .radius(2000) //500 미터 내에서 검색
                .type(PlaceType.CAFE)
                .type(PlaceType.BAKERY)
                .build()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public void showPlaceInformation_cvstore(LatLng location) {
        mMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(MyLocationActivity.this)
                .key("AIzaSyDdlA0zHcNZ4wC1_DA6k3hg0_2tG91JzX8")
                //.latlng(location.latitude, location.longitude)//현재 위치
                .latlng(37.56, 126.97)   //임의로 위치 설정
                .radius(2000) //500 미터 내에서 검색
                .type(PlaceType.CONVENIENCE_STORE)
                .build()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public void showPlaceInformation_shopping(LatLng location) {
        mMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(MyLocationActivity.this)
                .key("AIzaSyDdlA0zHcNZ4wC1_DA6k3hg0_2tG91JzX8")
                //.latlng(location.latitude, location.longitude)//현재 위치
                .latlng(37.56, 126.97)   //임의로 위치 설정
                .radius(2000) //500 미터 내에서 검색
                .type(PlaceType.DEPARTMENT_STORE)
                .type(PlaceType.JEWELRY_STORE)
                .type(PlaceType.CLOTHING_STORE)
                .type(PlaceType.LIQUOR_STORE)
                .type(PlaceType.SHOE_STORE)
                .build()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public void showPlaceInformation_sights(LatLng location) {
        mMap.clear();//지도 클리어

        if (previous_marker != null)
            previous_marker.clear();//지역정보 마커 클리어

        new NRPlaces.Builder()
                .listener(MyLocationActivity.this)
                .key("AIzaSyDdlA0zHcNZ4wC1_DA6k3hg0_2tG91JzX8")
                //.latlng(location.latitude, location.longitude)//현재 위치
                .latlng(37.56, 126.97)   //임의로 위치 설정
                .radius(2000) //500 미터 내에서 검색
                .type(PlaceType.AMUSEMENT_PARK)
                .type(PlaceType.MUSEUM)
                .type(PlaceType.ART_GALLERY)
                .type(PlaceType.AQUARIUM)
                .type(PlaceType.MOVIE_THEATER)
                .type(PlaceType.STADIUM)
                .type(PlaceType.ZOO)
                .type(PlaceType.SPA)
                .type(PlaceType.MOVIE_RENTAL)
                .type(PlaceType.CASINO)
                .type(PlaceType.STADIUM)
                .type(PlaceType.CITY_HALL)
                .type(PlaceType.PARK)
                .build()
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


}
