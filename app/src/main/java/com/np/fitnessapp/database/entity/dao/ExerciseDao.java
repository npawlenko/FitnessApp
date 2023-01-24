package com.np.fitnessapp.database.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.relation.ExerciseWithExerciseRecords;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertExercises(Exercise exercise);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(Exercise... exercise);
    @Update
    void updateExercises(Exercise exercise);
    @Delete
    void deleteExercises(Exercise exercise);


    @Query("SELECT * FROM exercise WHERE exercise.exerciseId = :exerciseId")
    Exercise getExerciseById(long exerciseId);
    @Query("SELECT * FROM exercise")
    List<Exercise> getAllExercises();
    @Transaction
    @Query("SELECT * FROM exercise")
    List<ExerciseWithExerciseRecords> getAllExerciseWithExerciseRecords();
}
