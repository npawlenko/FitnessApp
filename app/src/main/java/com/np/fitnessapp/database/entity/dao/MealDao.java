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
    void insertMeals(Meal... meals);
    @Update
    void updateMeals(Meal... meals);
    @Delete
    void deleteMeals(Meal... meals);


    @Query("SELECT * FROM meal WHERE meal.mealId = :mealId")
    Meal getMealById(long mealId);
    @Query("SELECT * FROM meal")
    List<Meal> getAllMeals();
    @Transaction
    @Query("SELECT * FROM meal")
    List<MealWithMealRecords> getAllMealWithMealRecords();
}
