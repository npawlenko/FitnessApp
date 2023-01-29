package com.np.fitnessapp.ui.viewadapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.np.fitnessapp.activity.ExerciseActivity;
import com.np.fitnessapp.activity.MealActivity;
import com.np.fitnessapp.database.entity.relation.MealRecordWithMeal;
import com.np.fitnessapp.databinding.FragmentMealBinding;

import java.util.List;

public class ListMealRecyclerViewAdapter extends RecyclerView.Adapter<ListMealRecyclerViewAdapter.ViewHolder> {

    private final List<MealRecordWithMeal> mealRecords;

    public ListMealRecyclerViewAdapter(List<MealRecordWithMeal> items) {
        mealRecords = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentMealBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mealRecord = mealRecords.get(position);
        if(holder.mealRecord != null) {
            holder.mIdView.setText("" + mealRecords.get(position).meal.mealId);
            holder.mContentView.setText(mealRecords.get(position).meal.name);
        }
    }

    @Override
    public int getItemCount() {
        return mealRecords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mIdView;
        public final TextView mContentView;
        public MealRecordWithMeal mealRecord;

        public ViewHolder(FragmentMealBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MealActivity.class);
            intent.putExtra(MealActivity.MEAL_ID_EXTRA, mealRecord.meal.mealId);
            view.getContext().startActivity(intent);
        }

        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}