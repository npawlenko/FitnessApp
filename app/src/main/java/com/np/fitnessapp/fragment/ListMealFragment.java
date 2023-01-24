package com.np.fitnessapp.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.np.fitnessapp.R;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.MealRecord;
import com.np.fitnessapp.database.entity.dao.MealDao;
import com.np.fitnessapp.database.entity.dao.MealRecordDao;
import com.np.fitnessapp.database.entity.relation.MealRecordWithMeal;
import com.np.fitnessapp.fragment.placeholder.PlaceholderContent;
import com.np.fitnessapp.fragment.viewadapter.ListMealRecyclerViewAdapter;

import java.util.List;

public class ListMealFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private TextView emptyTextView;


    public ListMealFragment() {
    }

    @SuppressWarnings("unused")
    public static ListMealFragment newInstance(int columnCount) {
        ListMealFragment fragment = new ListMealFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
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