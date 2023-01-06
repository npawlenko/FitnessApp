package com.np.fitnessapp.database.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.np.fitnessapp.database.entity.MealRecord;
import com.np.fitnessapp.database.entity.User;

import java.util.List;

public class UserWithMealRecords {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "mealRecordId"
    )
    public List<MealRecord> meals;
}
