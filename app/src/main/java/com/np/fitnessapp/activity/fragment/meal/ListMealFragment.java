package com.np.fitnessapp.activity.fragment.meal;

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
import com.np.fitnessapp.database.entity.dao.MealRecordDao;
import com.np.fitnessapp.database.entity.relation.MealRecordWithMeal;
import com.np.fitnessapp.ui.viewadapter.ListMealRecyclerViewAdapter;

import java.util.List;

public class ListMealFragment extends Fragment {

    private TextView emptyTextView;


    public ListMealFragment() {
    }

    @SuppressWarnings("unused")
    public static ListMealFragment newInstance(int columnCount) {
        ListMealFragment fragment = new ListMealFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_list, container, false);
        emptyTextView = view.findViewById(R.id.emptyMealsTextView);

        MealRecordDao mealRecordDao = AppDatabase.getDatabase(getContext()).mealRecordDao();

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        List<MealRecordWithMeal> records =  mealRecordDao.getTodayMealRecordsWithMeal();
        if(records.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setAdapter(new ListMealRecyclerViewAdapter(records));
        }
        return view;
    }
}