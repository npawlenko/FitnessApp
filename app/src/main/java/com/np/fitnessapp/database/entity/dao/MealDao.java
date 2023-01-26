package com.np.fitnessapp.database.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.np.fitnessapp.database.entity.Meal;
import com.np.fitnessapp.database.entity.relation.MealWithMealRecords;

import java.util.List;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMeal(Meal meal);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Meal... meals);
    @Update
    void updateMeal(Meal meal);
    @Delete
    void deleteMeal(Meal meal);


    @Query("SELECT * FROM meal WHERE meal.mealId = :mealId")
    Meal getMealById(long mealId);
    @Query("SELECT * FROM meal")
    List<Meal> getAllMeals();
    @Query("SELECT * FROM meal WHERE name LIKE '%' || :like || '%'")
    List<Meal> getMealsLike(String like);
    @Transaction
    @Query("SELECT * FROM meal")
    List<MealWithMealRecords> getAllMealWithMealRecords();
}
