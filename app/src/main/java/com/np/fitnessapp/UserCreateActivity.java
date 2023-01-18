package com.np.fitnessapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class UserCreateActivity extends AppCompatActivity {

    public static final String CREATED_USER_ID_EXTRA = "createdUserId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
    }
}