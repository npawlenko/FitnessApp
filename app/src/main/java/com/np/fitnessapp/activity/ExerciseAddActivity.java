package com.np.fitnessapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.np.fitnessapp.R;
import com.np.fitnessapp.RetrofitInstance;
import com.np.fitnessapp.api.exercises.ApiExercise;
import com.np.fitnessapp.api.food.Product;
import com.np.fitnessapp.api.food.ProductsContainer;
import com.np.fitnessapp.api.service.ExerciseService;
import com.np.fitnessapp.api.service.ProductService;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.Meal;
import com.np.fitnessapp.database.entity.dao.ExerciseDao;
import com.np.fitnessapp.database.entity.dao.MealDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseAddActivity extends AppCompatActivity {

    public static final String EXERCISE_ID_EXTRA = "exerciseId";

    private ExerciseAdapter exerciseAdapter;
    private SearchView searchView;
    private ListView exerciseListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_add);

        ExerciseDao exerciseDao = AppDatabase.getDatabase(getApplication()).exerciseDao();
        searchView = findViewById(R.id.searchExercise);
        exerciseListView = findViewById(R.id.exercisesSearchList);

        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise exercise = (Exercise) adapterView.getItemAtPosition(i);
                Intent result = new Intent();
                result.putExtra(EXERCISE_ID_EXTRA, exercise.exerciseId);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        List<Exercise> exerciseList = exerciseDao.getAllExercises();
        exerciseAdapter = new ExerciseAdapter(exerciseList, this);
        exerciseListView.setAdapter(exerciseAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchExercises(s);
                exerciseAdapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    public class ExerciseAdapter extends BaseAdapter {

        private List<Exercise> list;
        private List<Exercise> filtered;
        private Context context;

        public ExerciseAdapter(List<Exercise> list, Context context) {
            this.list = list;
            this.filtered = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return filtered.size();
        }

        @Override
        public Object getItem(int i) {
            return filtered.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.exercise_list_element, null);

            TextView nameTextView = view.findViewById(R.id.exercise_name);

            nameTextView.setText(filtered.get(i).name);

            return view;
        }

        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();

                    if(charSequence == null || charSequence.length() == 0) {
                        results.count = list.size();
                        results.values = list;
                    }
                    else {
                        List<Exercise> resultData = new ArrayList<>();

                        for(Exercise exercise : list) {
                            if(exercise.name.toLowerCase().contains(charSequence)) {
                                resultData.add(exercise);
                            }
                        }

                        results.count = resultData.size();
                        results.values = resultData;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filtered = (List<Exercise>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }


    private void searchExercises(String q) {
        ExerciseService exerciseService = RetrofitInstance.getExerciseRetrofitInstance().create(ExerciseService.class);
        Call<List<ApiExercise>> call = exerciseService.searchForExercise(q);

        call.enqueue(new Callback<List<ApiExercise>>() {
            @Override
            public void onResponse(Call<List<ApiExercise>> call, Response<List<ApiExercise>> response) {
                List<ApiExercise> exercises = response.body();
                if(exercises != null && exercises.size() > 0) {
                    ExerciseDao exerciseDao = AppDatabase.getDatabase(getApplication()).exerciseDao();
                    List<Exercise> result = new ArrayList<>();
                    for(ApiExercise e : exercises) {
                        Exercise e1 = new Exercise();
                        e1.name = e.name;
                        e1.caloriesPerHour = Integer.parseInt(e.calories);
                        long id = exerciseDao.insertExercises(e1);
                        e1.exerciseId = id;
                        result.add(e1);
                    }

                    exerciseAdapter.filtered = exerciseDao.getExercisesLike(q);
                    exerciseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ApiExercise>> call, Throwable t) {

            }
        });
    }
}