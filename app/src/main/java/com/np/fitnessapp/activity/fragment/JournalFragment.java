package com.np.fitnessapp.activity.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.np.fitnessapp.R;
import com.np.fitnessapp.activity.ExerciseAddActivity;
import com.np.fitnessapp.activity.MealAddActivity;
import com.np.fitnessapp.activity.UserCreateActivity;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.ExerciseRecord;
import com.np.fitnessapp.database.entity.Meal;
import com.np.fitnessapp.database.entity.MealRecord;
import com.np.fitnessapp.database.entity.dao.ExerciseRecordDao;
import com.np.fitnessapp.database.entity.dao.MealDao;
import com.np.fitnessapp.database.entity.dao.MealRecordDao;

import java.util.Date;

public class JournalFragment extends Fragment {

    private Button addMealButton;
    private Button addExerciseButton;

    private ActivityResultLauncher<Intent> selectMealLauncher;
    private ActivityResultLauncher<Intent> selectExerciseLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectMealLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != RESULT_OK) {
                        return;
                    }

                    Intent data = result.getData();
                    long mealId = data.getLongExtra(MealAddActivity.MEAL_ID_EXTRA, -1);
                    AppDatabase db = AppDatabase.getDatabase(getContext());
                    MealRecordDao mealRecordDao = db.mealRecordDao();
                    Meal meal = db.mealDao().getMealById(mealId);

                    MealRecord mealRecord = new MealRecord();
                    mealRecord.mealId = mealId;
                    mealRecord.date = new Date();
                    mealRecord.portions = 1;
                    mealRecord.totalCalories = meal.calories;
                    mealRecordDao.insertMealRecord(mealRecord);
                }
        );

        selectExerciseLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != RESULT_OK) {
                        return;
                    }

                    Intent data = result.getData();
                    long exerciseId = data.getLongExtra(ExerciseAddActivity.EXERCISE_ID_EXTRA, -1);
                    AppDatabase db = AppDatabase.getDatabase(getContext());
                    ExerciseRecordDao exerciseRecordDao = db.exerciseRecordDao();
                    Exercise exercise = db.exerciseDao().getExerciseById(exerciseId);

                    ExerciseRecord exerciseRecord = new ExerciseRecord();
                    exerciseRecord.exerciseId = exerciseId;
                    exerciseRecord.date = new Date();
                    exerciseRecord.totalHours = 1;
                    exerciseRecord.totalCalories = exercise.caloriesPerHour;
                    exerciseRecordDao.insertExerciseRecord(exerciseRecord);
                }
        );
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        addMealButton = view.findViewById(R.id.addMealButton);
        addExerciseButton = view.findViewById(R.id.addExerciseButton);

        addMealButton.setOnClickListener(v -> {


            Intent addMealIntent = new Intent(getActivity(), MealAddActivity.class);
            //getActivity().startActivity(addMealIntent);
            selectMealLauncher.launch(addMealIntent);
        });

        addExerciseButton.setOnClickListener(v -> {
            Intent addExerciseIntent = new Intent(getActivity(), ExerciseAddActivity.class);
            //getActivity().startActivity(addExerciseIntent);
            selectExerciseLauncher.launch(addExerciseIntent);
        });

        return view;
    }
}