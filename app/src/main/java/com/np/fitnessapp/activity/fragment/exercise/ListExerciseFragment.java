package com.np.fitnessapp.activity.fragment.exercise;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.np.fitnessapp.R;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.dao.ExerciseRecordDao;
import com.np.fitnessapp.database.entity.dao.MealRecordDao;
import com.np.fitnessapp.database.entity.relation.ExerciseRecordWithExercise;
import com.np.fitnessapp.database.entity.relation.MealRecordWithMeal;
import com.np.fitnessapp.ui.viewadapter.ListExerciseRecyclerViewAdapter;
import com.np.fitnessapp.ui.viewadapter.ListMealRecyclerViewAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListExerciseFragment extends Fragment {

    private TextView emptyTextView;
    private ListExerciseRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    public ListExerciseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        emptyTextView = view.findViewById(R.id.emptyExercisesTextView);

        ExerciseRecordDao exerciseRecordDao = AppDatabase.getDatabase(getContext()).exerciseRecordDao();

        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<ExerciseRecordWithExercise> records =  exerciseRecordDao.getTodayExerciseRecordWithExercise();
        if(records.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
        }
        else {
            adapter = new ListExerciseRecyclerViewAdapter(records);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    public void updateView() {
        ExerciseRecordDao exerciseRecordDao = AppDatabase.getDatabase(getContext()).exerciseRecordDao();
        List<ExerciseRecordWithExercise> records =  exerciseRecordDao.getTodayExerciseRecordWithExercise();
        emptyTextView.setVisibility(View.INVISIBLE);

        if(adapter == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new ListExerciseRecyclerViewAdapter(records);
            recyclerView.setAdapter(adapter);
            return;
        }

        adapter = new ListExerciseRecyclerViewAdapter(records);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}