package com.np.fitnessapp.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "exercise",
        indices = {
                @Index(value = "name", unique = true)
        }
)
public class Exercise {
    @PrimaryKey
    public long exerciseId;
    public String name;
    public int caloriesPerHour;
    public byte[] icon;

    public Exercise() {
    }

    @Ignore
    public Exercise(String name, int caloriesPerHour, byte[] icon) {
        this.name = name;
        this.caloriesPerHour = caloriesPerHour;
        this.icon = icon;
    }
}
