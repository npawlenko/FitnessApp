package com.np.fitnessapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.np.fitnessapp.FitnessApp;
import com.np.fitnessapp.activity.HomeActivity;
import com.np.fitnessapp.activity.user.UserCreateActivity;
import com.np.fitnessapp.activity.user.UserSelectActivity;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.User;
import com.np.fitnessapp.database.entity.dao.UserDao;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FitnessApp app = FitnessApp.getInstance();
        appPreferences = getSharedPreferences(FitnessApp.APP_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        UserDao userDao = db.userDao();
        Intent homeIntent = new Intent(this, HomeActivity.class);

        long savedUserId = appPreferences.getLong(FitnessApp.SAVED_USERID_KEY, -1);
        if (savedUserId != -1) {
            User savedUser = userDao.getUserById(savedUserId);
            if (savedUser != null) {
                app.setUser(savedUser);
                startActivity(homeIntent);
                return;
            }
        }

        if (userDao.getAllUsers().size() > 0) {
            ActivityResultLauncher<Intent> userSelectActivityLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() != RESULT_OK) {
                            return;
                        }

                        Intent data = result.getData();
                        long userId = data.getLongExtra(UserSelectActivity.SELECTED_USERID_EXTRA, -1);
                        app.setUser(userDao.getUserById(userId));
                        saveUserId(userId);
                        startActivity(homeIntent);
                    }
            );

            Intent selectUserIntent = new Intent(this, UserSelectActivity.class);
            userSelectActivityLauncher.launch(selectUserIntent);
        } else {
            ActivityResultLauncher<Intent> userCreateActivityLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() != RESULT_OK) {
                            return;
                        }

                        Intent data = result.getData();
                        long userId = data.getLongExtra(UserCreateActivity.CREATED_USER_ID_EXTRA, -1);
                        app.setUser(userDao.getUserById(userId));
                        saveUserId(userId);
                        startActivity(homeIntent);
                    }
            );

            Intent createUserIntent = new Intent(this, UserCreateActivity.class);
            userCreateActivityLauncher.launch(createUserIntent);
        }
    }

    private void saveUserId(long userId) {
        appPreferences.edit()
                .putLong(FitnessApp.SAVED_USERID_KEY, userId)
                .apply();
    }
}