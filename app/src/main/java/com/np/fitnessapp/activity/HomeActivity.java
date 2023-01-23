package com.np.fitnessapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.np.fitnessapp.FitnessApp;
import com.np.fitnessapp.R;
import com.np.fitnessapp.activity.user.UserSelectActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mealsRecyclerView;
    private RecyclerView exercisesRecyclerView;
    private PieChart pieChart;
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FitnessApp app = FitnessApp.getInstance();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch(id){
                case R.id.page_2:
                    Intent diaryIntent = new Intent(this, DiaryActivity.class);
                    startActivity(diaryIntent);
                    break;
                case R.id.page_3:
                    Intent userSelectIntent = new Intent(this, UserSelectActivity.class);
                    startActivity(userSelectIntent);
                default:
            }
            return true;
        });

        welcomeTextView = findViewById(R.id.welcome_textview);
        welcomeTextView.setText(getString(R.string.welcome, app.getUser().name));

        pieChart = findViewById(R.id.home_piechart);
        setupPieChart();
        loadPieChartData();

        mealsRecyclerView = findViewById(R.id.meals_recyclerview);
        exercisesRecyclerView = findViewById(R.id.exercises_recyclerview);
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setEnabled(false);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f, "Spalone kalorie"));
        entries.add(new PieEntry(0.6f, "Spożyte kalorie"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();
    }
}