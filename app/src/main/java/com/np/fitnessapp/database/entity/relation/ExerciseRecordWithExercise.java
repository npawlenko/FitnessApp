package com.np.fitnessapp.database.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.ExerciseRecord;

public class ExerciseRecordWithExercise {
    @Embedded
    public ExerciseRecord exerciseRecord;
    @Relation(
            parentColumn = "exerciseRecordId",
            entityColumn = "exerciseId"
    )
    public Exercise exercise;
}
