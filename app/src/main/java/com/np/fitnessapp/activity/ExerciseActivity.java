package com.np.fitnessapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.np.fitnessapp.R;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.dao.ExerciseDao;

public class ExerciseActivity extends AppCompatActivity {

    public static final String EXERCISE_ID_EXTRA = "exerciseId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras.isEmpty()) {
            finish();
            return;
        }

        long exerciseId = extras.getLong(EXERCISE_ID_EXTRA);
        ExerciseDao exerciseDao = AppDatabase.getDatabase(getApplicationContext()).exerciseDao();
        Exercise exercise = exerciseDao.getExerciseById(exerciseId);
        if(exercise == null) {
            finish();
            return;
        }

        TextView nameTextView = findViewById(R.id.exercise_name_textview);
        TextView cphTextView = findViewById(R.id.exercise_cph_textview);
        nameTextView.setText(exercise.name);
        cphTextView.setText(exercise.caloriesPerHour + "");
    }
}