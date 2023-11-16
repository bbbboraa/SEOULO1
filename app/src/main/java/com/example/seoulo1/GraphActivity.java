package com.example.seoulo1;


import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GraphActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    static final String DB_NAME = "schedule.db";
    static final String TABLE_NAME = "schedule";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        ScheduleDbHelper dbHelper=new ScheduleDbHelper(this);
        db=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        db=dbHelper.getReadableDatabase();

        // 총 지출을 계산하고 설정
        double totalExpense = dbHelper.getTotalExpense(db);
        TextView totalExpenseEditText = findViewById(R.id.edittext_total_expense);
        totalExpenseEditText.setText(String.valueOf((int)totalExpense));

        // 카테고리 지출을 계산하고 설정
        setCategoryExpense("음식점", R.id.restaurant_won, R.id.restaurant_percent);
        setCategoryExpense("카페", R.id.cafe_won, R.id.cafe_percent);
        setCategoryExpense("문화/관광", R.id.tour_won, R.id.tour_percent);
        setCategoryExpense("쇼핑", R.id.shop_won, R.id.shop_percent);
        setCategoryExpense("기타", R.id.other_won, R.id.other_percent);

        // 프로그레스 바 설정
        setProgressBar(R.id.restaurant_bar, R.id.restaurant_percent);
        setProgressBar(R.id.cafe_bar, R.id.cafe_percent);
        setProgressBar(R.id.tour_bar, R.id.tour_percent);
        setProgressBar(R.id.shop_bar, R.id.shop_percent);
        setProgressBar(R.id.otherbar, R.id.other_percent);
    }

    private void setCategoryExpense(String category, int wonTextViewId, int percentTextViewId) {
        ScheduleDbHelper dbHelper = new ScheduleDbHelper(this);

        // 카테고리 지출을 계산하고 설정
        double categoryExpense = dbHelper.getCategoryExpense(db, category);
        TextView categoryWonTextView = findViewById(wonTextViewId);
        categoryWonTextView.setText(String.valueOf((int)categoryExpense));

        // 카테고리 백분율을 계산하고 설정
        double totalExpense = dbHelper.getTotalExpense(db);
        double categoryPercentage = (categoryExpense / totalExpense) * 100;
        TextView categoryPercentTextView = findViewById(percentTextViewId);
        categoryPercentTextView.setText(String.format("%.2f", categoryPercentage) + "%");
    }

    private void setProgressBar(int progressBarId, int percentTextViewId) {
        ProgressBar progressBar = findViewById(progressBarId);
        TextView percentTextView = findViewById(percentTextViewId);

        // 프로그레스 바 설정
        try {
            double categoryPercentage = Double.parseDouble(percentTextView.getText().toString().replace("%", ""));
            progressBar.setProgress((int) categoryPercentage);

            // Set progress bar color
            progressBar.getProgressDrawable().setColorFilter(
                    Color.parseColor("#1E285C"),
                    android.graphics.PorterDuff.Mode.SRC_IN
            );

            Log.d("ProgressBar", "Progress set: " + progressBar.getProgress());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
