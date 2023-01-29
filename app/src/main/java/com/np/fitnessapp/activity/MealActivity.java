package com.np.fitnessapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.np.fitnessapp.R;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.Meal;
import com.np.fitnessapp.database.entity.dao.ExerciseDao;
import com.np.fitnessapp.database.entity.dao.MealDao;

public class MealActivity extends AppCompatActivity {

    public static final String MEAL_ID_EXTRA = "mealId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_meal);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras.isEmpty()) {
            finish();
            return;
        }

        long mealId = extras.getLong(MEAL_ID_EXTRA);
        MealDao mealDao = AppDatabase.getDatabase(getApplicationContext()).mealDao();
        Meal meal = mealDao.getMealById(mealId);
        if(meal == null) {
            finish();
            return;
        }

        TextView nameTextView = findViewById(R.id.meal_name_textview);
        TextView caloriesTextView = findViewById(R.id.meal_calories_textview);
        nameTextView.setText(meal.name);
        caloriesTextView.setText(meal.calories + "");
    }
}