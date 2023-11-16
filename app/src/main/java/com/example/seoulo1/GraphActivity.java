package com.example.seoulo1;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class GraphActivity extends AppCompatActivity {

    private ProgressBar progressBarRestaurant, progressBarCafe, progressBarTour, progressBarShop, progressBarOther;
    private TextView restaurantPercent, cafePercent, tourPercent, shopPercent, otherPercent;
    private TextView totalExpenseEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Initialize UI elements
        progressBarRestaurant = findViewById(R.id.restaurant_bar);
        progressBarCafe = findViewById(R.id.cafe_bar);
        progressBarTour = findViewById(R.id.tour_bar);
        progressBarShop = findViewById(R.id.shop_bar);
        progressBarOther = findViewById(R.id.otherbar);

        restaurantPercent = findViewById(R.id.restaurant_percent);
        cafePercent = findViewById(R.id.cafe_percent);
        tourPercent = findViewById(R.id.tour_percent);
        shopPercent = findViewById(R.id.shop_percent);
        otherPercent = findViewById(R.id.other_percent);

        totalExpenseEditText = findViewById(R.id.edittext_total_expense);

        // Fetch data from the database and update UI
        updateUIFromDatabase();
    }

    private void updateCategoryData(SQLiteDatabase db, String category, ProgressBar progressBar, TextView percentTextView) {
        ScheduleDbHelper dbHelper = new ScheduleDbHelper(this);
        double categoryExpense = dbHelper.getCategoryExpense(db, category);
        double totalExpense = dbHelper.getTotalExpense(db);

        double categoryPercentage = (categoryExpense / totalExpense) * 100;
        int progress = (int) Math.min(Math.max(categoryPercentage, 0), 100);
        progressBar.setProgress(progress);

        percentTextView.setText(String.format(Locale.getDefault(), "%.2f%%", categoryPercentage));

        TextView categoryWonTextView = getCategoryWonTextView(progressBar.getId());
        categoryWonTextView.setText(String.format(Locale.getDefault(), "%.2f", categoryExpense));

        int[] androidColors = getResources().getIntArray(R.array.androidColors);
        int randomColor = androidColors[(int) (Math.random() * androidColors.length)];
        progressBar.setProgressTintList        (android.content.res.ColorStateList.valueOf(randomColor));
    }

    private TextView getCategoryWonTextView(int progressBarId) {
        switch (progressBarId) {
            case R.id.restaurant_bar:
                return findViewById(R.id.restaurant_won);
            case R.id.cafe_bar:
                return findViewById(R.id.cafe_won);
            case R.id.tour_bar:
                return findViewById(R.id.tour_won);
            case R.id.shop_bar:
                return findViewById(R.id.shop_won);
            case R.id.otherbar:
                return findViewById(R.id.other_won);
            default:
                return null;
        }
    }

    private void updateUIFromDatabase() {
        ScheduleDbHelper dbHelper = new ScheduleDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        updateCategoryData(db, "Restaurant", progressBarRestaurant, restaurantPercent);
        updateCategoryData(db, "Cafe", progressBarCafe, cafePercent);
        updateCategoryData(db, "Culture/Tourism", progressBarTour, tourPercent);
        updateCategoryData(db, "Shopping", progressBarShop, shopPercent);
        updateCategoryData(db, "Other", progressBarOther, otherPercent);

        totalExpenseEditText.setText(String.valueOf(dbHelper.getTotalExpense(db)));

        db.close();
    }

}
