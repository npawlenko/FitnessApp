package com.np.fitnessapp.database.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.np.fitnessapp.database.entity.Meal;
import com.np.fitnessapp.database.entity.MealRecord;

import java.util.List;

public class MealWithMealRecords {
    @Embedded
    public Meal meal;
    @Relation(
            parentColumn = "mealId",
            entityColumn = "mealRecordId"
    )
    public List<MealRecord> records;
}
