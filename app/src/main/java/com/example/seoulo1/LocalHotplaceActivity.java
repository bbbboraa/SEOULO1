package com.example.seoulo1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;





public class LocalHotplaceActivity extends AppCompatActivity {
    private ImageButton like_btn, menu_btn, list_location;
    private Button button_restaurant, button_cafe, button_shopping, button_tour;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_hotplace);
        listView = findViewById(R.id.listView);

        button_restaurant = findViewById(R.id.button_restaurant);
        button_restaurant.setOnClickListener(v -> {;

            String json = readJSONFile();
            List<String> foodList = extractFoodDataFromJSON(json);
            showDataInListView(foodList);
        });

        button_cafe = findViewById(R.id.button_cafe);
        button_cafe.setOnClickListener(v -> {
            String json = readJSONFile();
            List<String> cafeList = extractCafeDataFromJSON(json);
            showDataInListView(cafeList);
        });

        button_tour = findViewById(R.id.button_tour);
        button_tour.setOnClickListener(v -> {

            String json = readJSONFile();
            List<String> tourList = extractTourDataFromJSON(json);
            showDataInListView(tourList);
        });

        button_shopping = findViewById(R.id.button_shopping);
        button_shopping.setOnClickListener(v -> {

            String json = readJSONFile();
            List<String> shopList = extractShopDataFromJSON(json);
            showDataInListView(shopList);
        });

        menu_btn = findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(view -> {

            //PopupMenu 객체 생성
            PopupMenu popup = new PopupMenu(LocalHotplaceActivity.this, view); //두 번째 파라미터가 팝업메뉴가 붙을 뷰
            //PopupMenu popup= new PopupMenu(MainActivity.this, btn2); //첫번째 버튼을 눌렀지만 팝업메뉴는 btn2에 붙어서 나타남
            getMenuInflater().inflate(R.menu.popup, popup.getMenu());

            //팝업메뉴의 메뉴아이템을 선택하는 것을 듣는 리스너 객체 생성 및 설정
            popup.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()) {
                    case R.id.menu_my_location:
                        Intent intent1 = new Intent(LocalHotplaceActivity.this, MyLocationActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.menu_hot_place:
                        Intent intent2 = new Intent(LocalHotplaceActivity.this, LocalSelectActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.menu_route:
                        Intent intent3 = new Intent(LocalHotplaceActivity.this, CalendarActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menu_checklist:
                        Intent intent4 = new Intent(LocalHotplaceActivity.this, CheckListActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.menu_travel_log:
                        Intent intent5 = new Intent(LocalHotplaceActivity.this, PlanActivity.class);
                        startActivity(intent5);
                        break;
                    case R.id.menu_expense_graph:
                        break;
                    case R.id.menu_mypage:
                        Intent intent7 = new Intent(LocalHotplaceActivity.this, MypageActivity.class);
                        startActivity(intent7);
                        break;
                }

                return false;
            });
            popup.show();
        });
    }

    private String readJSONFile() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("seoul_local.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private void showDataInListView(List<String> dataList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
    }

    private List<String> extractCafeDataFromJSON(String json) {
        List<String> cafeList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject gangseoObject = jsonObject.getJSONObject("강서구");
            JSONArray cafeArray = gangseoObject.getJSONArray("cafe");
            for (int i = 0; i < cafeArray.length(); i++) {
                String cafe = cafeArray.getString(i);
                cafeList.add(cafe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cafeList;
    }

    private List<String> extractFoodDataFromJSON(String json){
        List<String> foodList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject gangseoObject = jsonObject.getJSONObject("강서구");
            JSONArray foodArray = gangseoObject.getJSONArray("food");
            for (int i = 0; i < foodArray.length(); i++) {
                String food = foodArray.getString(i);
                foodList.add(food);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    private List<String> extractTourDataFromJSON(String json){
        List<String> tourList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject gangseoObject = jsonObject.getJSONObject("강서구");
            JSONArray tourArray = gangseoObject.getJSONArray("tour");
            for (int i = 0; i < tourArray.length(); i++) {
                String tour = tourArray.getString(i);
                tourList.add(tour);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tourList;
    }

    private List<String> extractShopDataFromJSON(String json){
        List<String> shopList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject gangseoObject = jsonObject.getJSONObject("강서구");
            JSONArray shopArray = gangseoObject.getJSONArray("shop");
            for (int i = 0; i < shopArray.length(); i++) {
                String tour = shopArray.getString(i);
                shopList.add(tour);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return shopList;
    }
}