package com.example.seoulo1;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;






public class LocalHotplaceActivity extends AppCompatActivity {
    private ImageButton like_btn, menu_btn, list_location;
    private Button button_restaurant, button_cafe, button_shopping, button_tour;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_hotplace);

        listView = findViewById(R.id.listView);
        button_restaurant = findViewById(R.id.button_restaurant);
        button_cafe =  findViewById(R.id.button_cafe);
        button_tour = findViewById(R.id.button_tour);
        button_shopping =  findViewById(R.id.button_shopping);

        // 추가 코드: 처음에 button_restaurant 버튼을 클릭한 것과 같은 효과를 얻기 위해 performClick() 호출
        button_restaurant.performClick();

        button_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayListFromAsset("gangseo_food.txt");
            }
        });

        button_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayListFromAsset("gangseo_cafe.txt");
            }
        });

        button_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayListFromAsset("gangseo_tour.txt");
            }
        });

        button_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayListFromAsset("gangseo_shop.txt");
            }
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

    private void displayListFromAsset(String fileName) {
        ArrayList<HotplaceItem> itemList = new ArrayList<>();

        try {
            InputStream inputStream = getAssets().open("Seoul/Gangseo/" + fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                HotplaceItem hotplaceItem = new HotplaceItem(line);
                itemList.add(hotplaceItem);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HotplaceAdapter adapter = new HotplaceAdapter(this, R.layout.list_view_items_2, itemList);
        listView.setAdapter(adapter);
    }

}