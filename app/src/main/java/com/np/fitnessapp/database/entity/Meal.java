package com.np.fitnessapp.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "meal",
        indices = {
                @Index(value = "name", unique = true)
        }
)
public class Meal {
    @PrimaryKey(autoGenerate = true)
    public long mealId;
    public String name;
    public int calories;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] icon;

    public Meal() {
    }

    @Ignore
    public Meal(String name, int calories, byte[] icon) {
        this.name = name;
        this.calories = calories;
        this.icon = icon;
    }
}
