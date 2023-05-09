package com.example.seoulo1;

import static android.opengl.Matrix.length;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocalSelectActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_select);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.localmap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        int i, j;
        double addr[] = {37.56100278, 126.9996417, 37.57037778, 126.9816417, 37.5360944, 126.9675222, 37.57636667, 126.9388972
                        , 37.571625, 127.0421417, 37.58638333, 127.0203333, 37.56061111, 127.039, 37.56070556, 126.9105306 };
                                  //중구                    //종로구                   //용산구                //서대문구
                                  //동대문구                 //성북구                   //성동구                //마포구
        String marker[] = {"중구", "종로구", "용산구", "서대문구", "동대문구", "성북구", "성동구", "마포구"};
        for (i = 0; i< addr.length; i++){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(marker[i]);
            j = i*2;
            markerOptions.position(new LatLng(addr[j], addr[j+1]));
            mMap.addMarker(markerOptions);
        }
    }
}