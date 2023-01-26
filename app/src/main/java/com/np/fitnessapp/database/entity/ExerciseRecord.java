package com.np.fitnessapp.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "exercise_record",
        foreignKeys = {
                @ForeignKey(
                        entity = Exercise.class,
                        parentColumns = "exerciseId",
                        childColumns = "exerciseId",
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
public class ExerciseRecord {
    @PrimaryKey(autoGenerate = true)
    public long exerciseRecordId;
    public long exerciseId;
    public int totalCalories;
    public float totalHours;
    public Date date;
    @ColumnInfo(defaultValue = "1")
    public long userId;
}
