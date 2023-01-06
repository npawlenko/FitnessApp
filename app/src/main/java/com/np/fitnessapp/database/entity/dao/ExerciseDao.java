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
    void insertExercises(Exercise... exercises);
    @Update
    void updateExercises(Exercise... exercises);
    @Delete
    void deleteExercises(Exercise... exercises);


    @Query("SELECT * FROM exercise WHERE exercise.exerciseId = :exerciseId")
    Exercise getExerciseById(long exerciseId);
    @Query("SELECT * FROM exercise")
    List<Exercise> getExercises();
    @Transaction
    @Query("SELECT * FROM exercise")
    List<ExerciseWithExerciseRecords> getAllExerciseWithExerciseRecords();
}
