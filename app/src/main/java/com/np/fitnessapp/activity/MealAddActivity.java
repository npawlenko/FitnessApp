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
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.Meal;
import com.np.fitnessapp.database.entity.dao.MealDao;
import com.np.fitnessapp.api.food.Product;
import com.np.fitnessapp.api.food.ProductsContainer;
import com.np.fitnessapp.api.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealAddActivity extends AppCompatActivity {

    public static final String MEAL_ID_EXTRA = "mealId";

    private MealAdapter mealAdapter;
    private SearchView searchView;
    private ListView mealListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_add);

        MealDao mealDao = AppDatabase.getDatabase(getApplication()).mealDao();
        searchView = findViewById(R.id.searchMeals);
        mealListView = findViewById(R.id.mealSearchList);

        List<Meal> mealList = mealDao.getAllMeals();
        mealAdapter = new MealAdapter(mealList, this);
        mealListView.setAdapter(mealAdapter);

        mealListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = (Meal) adapterView.getItemAtPosition(i);
                Intent result = new Intent();
                result.putExtra(MEAL_ID_EXTRA, meal.mealId);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchMeals(s);
                mealAdapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               return false;
            }
        });
    }


    public class MealAdapter extends BaseAdapter {

        private List<Meal> list;
        private List<Meal> filtered;
        private Context context;

        public MealAdapter(List<Meal> list, Context context) {
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
                        List<Meal> resultData = new ArrayList<>();

                        for(Meal meal : list) {
                            if(meal.name.toLowerCase().contains(charSequence)) {
                                resultData.add(meal);
                            }
                        }

                        results.count = resultData.size();
                        results.values = resultData;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    filtered = (List<Meal>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }


    private void searchMeals(String q) {
        ProductService productService = RetrofitInstance.getFoodRetrofitInstance().create(ProductService.class);
        Call<ProductsContainer> call = productService.searchForProduct(q);

        call.enqueue(new Callback<ProductsContainer>() {
            @Override
            public void onResponse(Call<ProductsContainer> call, Response<ProductsContainer> response) {
                ProductsContainer container = response.body();
                if(container != null && container.count > 0) {
                    MealDao mealDao = AppDatabase.getDatabase(getApplication()).mealDao();
                    List<Meal> result = new ArrayList<>();
                    for(Product p : container.products) {
                        Meal m = new Meal();
                        m.name = p.name;
                        m.calories = (int) p.nutriments.totalEnergy;
                        m.iconUrl = p.imageUrl;
                        long id = mealDao.insertMeal(m);
                        m.mealId = id;
                        result.add(m);
                    }

                    mealAdapter.filtered = mealDao.getMealsLike(q);
                    mealAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ProductsContainer> call, Throwable t) {

            }
        });
    }
}