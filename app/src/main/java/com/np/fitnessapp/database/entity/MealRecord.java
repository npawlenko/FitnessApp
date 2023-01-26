package com.np.fitnessapp.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "meal_record",
        foreignKeys = {
                @ForeignKey(
                        entity = Meal.class,
                        parentColumns = "mealId",
                        childColumns = "mealId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class MealRecord {
    @PrimaryKey(autoGenerate = true)
    public long mealRecordId;
    public long mealId;
    public Date date;
    public int portions;
    public int totalCalories;
    @ColumnInfo(defaultValue = "1")
    public long userId;
}
