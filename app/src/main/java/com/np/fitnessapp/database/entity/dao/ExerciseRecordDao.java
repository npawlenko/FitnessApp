package com.np.fitnessapp.database.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.ExerciseRecord;
import com.np.fitnessapp.database.entity.MealRecord;
import com.np.fitnessapp.database.entity.relation.ExerciseRecordWithExercise;

import java.util.List;

@Dao
public interface ExerciseRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertExerciseRecord(ExerciseRecord record);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(ExerciseRecord... records);
    @Update
    void updateExerciseRecord(ExerciseRecord record);
    @Delete
    void deleteExerciseRecord(ExerciseRecord record);

    @Query("SELECT * FROM exercise_record WHERE exerciseRecordId = :exerciseRecordId")
    ExerciseRecord getExerciseRecordById(long exerciseRecordId);
    @Query("SELECT * FROM exercise_record")
    List<ExerciseRecord> getAllExerciseRecords();
    @Query("SELECT * FROM exercise_record WHERE DATE(date) = DATE('now')")
    List<ExerciseRecord> getTodayExerciseRecords();
    @Transaction
    @Query("SELECT * FROM exercise_record WHERE exerciseRecordId = :exerciseRecordId")
    ExerciseRecordWithExercise getExerciseByExerciseRecordId(long exerciseRecordId);
    @Transaction
    @Query("SELECT * FROM exercise_record WHERE DATE(date) = DATE('now')")
    List<ExerciseRecordWithExercise> getTodayExerciseRecordWithExercise();
}
