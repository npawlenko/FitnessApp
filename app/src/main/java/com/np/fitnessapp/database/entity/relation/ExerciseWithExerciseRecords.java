package com.np.fitnessapp.database.entity.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.ExerciseRecord;

import java.util.List;

public class ExerciseWithExerciseRecords {
    @Embedded
    public Exercise exercise;
    @Relation(
            parentColumn = "exerciseId",
            entityColumn = "exerciseRecordId"
    )
    public List<ExerciseRecord> records;
}
