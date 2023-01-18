package com.np.fitnessapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.np.fitnessapp.database.entity.Exercise;
import com.np.fitnessapp.database.entity.ExerciseRecord;
import com.np.fitnessapp.database.entity.Meal;
import com.np.fitnessapp.database.entity.MealRecord;
import com.np.fitnessapp.database.entity.User;
import com.np.fitnessapp.database.entity.converters.DateConverter;
import com.np.fitnessapp.database.entity.dao.ExerciseDao;
import com.np.fitnessapp.database.entity.dao.ExerciseRecordDao;
import com.np.fitnessapp.database.entity.dao.MealDao;
import com.np.fitnessapp.database.entity.dao.MealRecordDao;
import com.np.fitnessapp.database.entity.dao.UserDao;

@Database(
        entities = {
                User.class,
                Exercise.class,
                ExerciseRecord.class,
                Meal.class,
                MealRecord.class
        },
        exportSchema = false,
        version = 1
)
@TypeConverters({
        DateConverter.class
})
public abstract class AppDatabase extends RoomDatabase {

    private static String DATABASE_NAME = "fitnessDb";

    private static volatile AppDatabase instance;

    public abstract ExerciseDao exerciseDao();
    public abstract ExerciseRecordDao exerciseRecordDao();
    public abstract MealDao mealDao();
    public abstract MealRecordDao mealRecordDao();
    public abstract UserDao userDao();

    public static AppDatabase getDatabase(final Context context) {
        if (instance != null) {
            return instance;
        }

        synchronized (AppDatabase.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }

            return instance;
        }
    }
}
