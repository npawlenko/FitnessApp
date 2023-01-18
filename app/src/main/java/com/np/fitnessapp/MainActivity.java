package com.np.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.User;
import com.np.fitnessapp.database.entity.dao.UserDao;

public class MainActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES_FILE_KEY = "com.np.fitnessapp.APP_PREFERENCES";
    public static final String SAVED_USERID_KEY = "savedUseridKey";

    private final AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
    private final SharedPreferences appPreferences = getSharedPreferences(APP_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserDao userDao = db.userDao();

        long savedUserId = appPreferences.getLong(SAVED_USERID_KEY, -1);
        if (savedUserId != -1) {
            User savedUser = userDao.getUserById(savedUserId);
            if (user != null) {
                user = savedUser;
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
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
                        user = userDao.getUserById(userId);
                        saveUserId(userId);
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
                        user = userDao.getUserById(userId);
                        saveUserId(userId);
                    }
            );

            Intent createUserIntent = new Intent(this, UserCreateActivity.class);
            userCreateActivityLauncher.launch(createUserIntent);
        }
    }

    private void saveUserId(long userId) {
        appPreferences.edit()
                .putLong(SAVED_USERID_KEY, userId)
                .apply();
    }
}