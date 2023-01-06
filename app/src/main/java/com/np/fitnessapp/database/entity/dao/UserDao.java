package com.np.fitnessapp.database.entity.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.np.fitnessapp.database.entity.User;
import com.np.fitnessapp.database.entity.relation.UserWithExerciseRecords;
import com.np.fitnessapp.database.entity.relation.UserWithMealRecords;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(User... users);
    @Update
    void updateUsers(User... users);
    @Delete
    void deleteUsers(User... users);


    @Query("SELECT * FROM user WHERE user.userId = :userId")
    User getUserById(long userId);
    @Query("SELECT * FROM user")
    List<User> getAllUsers();
    @Transaction
    @Query("SELECT * FROM user WHERE user.userId = :userId")
    List<UserWithExerciseRecords> getUserExerciseRecordsByUserId(long userId);
    @Transaction
    @Query("SELECT * FROM user WHERE user.userId = :userId")
    List<UserWithMealRecords> getUserMealRecordsByUserId(long userId);
}
