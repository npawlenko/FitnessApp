package com.np.fitnessapp.fragment.viewadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.np.fitnessapp.database.entity.relation.ExerciseRecordWithExercise;
import com.np.fitnessapp.databinding.FragmentExerciseBinding;
import com.np.fitnessapp.fragment.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ListExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ListExerciseRecyclerViewAdapter.ViewHolder> {

    private final List<ExerciseRecordWithExercise> mValues;

    public ListExerciseRecyclerViewAdapter(List<ExerciseRecordWithExercise> items) {
        mValues = items;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentExerciseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).exercise.exerciseId + "");
        holder.mContentView.setText(mValues.get(position).exercise.name);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public ExerciseRecordWithExercise item;

        public ViewHolder(FragmentExerciseBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}