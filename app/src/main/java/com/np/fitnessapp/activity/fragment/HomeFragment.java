package com.np.fitnessapp.activity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.np.fitnessapp.FitnessApp;
import com.np.fitnessapp.R;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.relation.ExerciseRecordWithExercise;
import com.np.fitnessapp.database.entity.relation.MealRecordWithMeal;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private PieChart pieChart;
    private TextView welcomeTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FitnessApp app = FitnessApp.getInstance();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        welcomeTextView = view.findViewById(R.id.welcome_textview);
        welcomeTextView.setText(getString(R.string.welcome, app.getUser().name));
        pieChart = view.findViewById(R.id.home_piechart);

        setupPieChart();
        loadPieChartData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupPieChart();
        loadPieChartData();
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
        AppDatabase db = AppDatabase.getDatabase(getContext());
        List<MealRecordWithMeal> todayMeals = db.mealRecordDao().getTodayMealRecordsWithMeal();
        List<ExerciseRecordWithExercise> todayExercises = db.exerciseRecordDao().getTodayExerciseRecordWithExercise();

        int caloriesBurn = 0;
        int caloriesEaten = 0;
        for(MealRecordWithMeal m : todayMeals) caloriesEaten += m.meal.calories;
        for(ExerciseRecordWithExercise e : todayExercises) caloriesBurn += e.exercise.caloriesPerHour;

        int caloriesTotal = caloriesBurn + caloriesEaten;

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) (caloriesBurn*100)/caloriesTotal, "Spalone kalorie"));
        entries.add(new PieEntry((float) (caloriesEaten*100)/caloriesTotal, "Spożyte kalorie"));

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