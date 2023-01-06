package com.np.fitnessapp.database.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.np.fitnessapp.database.entity.ExerciseRecord;
import com.np.fitnessapp.database.entity.User;

import java.util.List;

public class UserWithExerciseRecords {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "exerciseRecordId"
    )
    public List<ExerciseRecord> exercises;
}
