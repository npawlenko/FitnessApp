package com.np.fitnessapp.database.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.Meal;
import com.np.fitnessapp.database.entity.MealRecord;
import com.np.fitnessapp.database.entity.relation.MealRecordWithMeal;

import java.util.List;

@Dao
public interface MealRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMealRecord(MealRecord record);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(MealRecord... records);
    @Update
    void updateMealRecord(MealRecord record);
    @Delete
    void deleteMealRecord(MealRecord record);

    @Query("SELECT * FROM meal_record WHERE mealRecordId = :mealRecordId")
    MealRecord getMealRecordById(long mealRecordId);
    @Query("SELECT * FROM meal_record")
    List<MealRecord> getAllMealRecords();
    @Transaction
    @Query("SELECT * FROM meal_record WHERE mealRecordId = :mealRecordId")
    MealRecordWithMeal getAllMealRecordsWithMeal(long mealRecordId);
}
