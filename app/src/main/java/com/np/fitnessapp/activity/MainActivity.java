package com.np.fitnessapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.np.fitnessapp.FitnessApp;
import com.np.fitnessapp.R;
import com.np.fitnessapp.activity.fragment.JournalFragment;
import com.np.fitnessapp.activity.fragment.HomeFragment;
import com.np.fitnessapp.activity.fragment.UserSelectFragment;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.User;
import com.np.fitnessapp.database.entity.dao.UserDao;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private SharedPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.page_home);


        FitnessApp app = FitnessApp.getInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        appPreferences = getSharedPreferences(FitnessApp.APP_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        UserDao userDao = db.userDao();

        long savedUserId = appPreferences.getLong(FitnessApp.SAVED_USERID_KEY, -1);
        if (savedUserId != -1) {
            User savedUser = userDao.getUserById(savedUserId);
            if (savedUser != null) {
                app.setUser(savedUser);
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.fragment_container, homeFragment).commit();
                return;
            }
        }

        if (userDao.getAllUsers().size() > 0) {
            UserSelectFragment userSelectFragment = new UserSelectFragment();
            fragmentTransaction.replace(R.id.fragment_container, userSelectFragment).commit();
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
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentTransaction.replace(R.id.fragment_container, homeFragment).commit();
                        bottomNavigationView.setSelectedItemId(R.id.page_home);
                    }
            );

            Intent createUserIntent = new Intent(this, UserCreateActivity.class);
            userCreateActivityLauncher.launch(createUserIntent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(FitnessApp.getInstance().getUser() != null) {
            switch (item.getItemId()) {
                case R.id.page_home:
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.fragment_container, homeFragment).commit();
                    break;
                case R.id.page_diary:
                    JournalFragment diaryFragment = new JournalFragment();
                    fragmentTransaction.replace(R.id.fragment_container, diaryFragment).commit();
                    break;
                case R.id.page_users:
                    UserSelectFragment userSelectFragment = new UserSelectFragment();
                    fragmentTransaction.replace(R.id.fragment_container, userSelectFragment).commit();
                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;
    }

    private void saveUserId(long userId) {
        appPreferences.edit()
                .putLong(FitnessApp.SAVED_USERID_KEY, userId)
                .apply();
    }
}