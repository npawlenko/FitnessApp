package com.np.fitnessapp.database.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.np.fitnessapp.database.entity.Meal;
import com.np.fitnessapp.database.entity.MealRecord;

public class MealRecordWithMeal {
    @Embedded
    public MealRecord mealRecord;
    @Relation(
            parentColumn = "mealRecordId",
            entityColumn = "mealId"
    )
    public Meal meal;
}
