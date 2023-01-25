package com.np.fitnessapp;

import com.np.fitnessapp.database.entity.User;

public class FitnessApp {

    private static volatile FitnessApp instance;

    public static FitnessApp getInstance() {
        if (instance != null) {
            return instance;
        }

        synchronized (FitnessApp.class) {
            if (instance == null) {
                instance = new FitnessApp();
            }
            return instance;
        }
    }


    public static final String APP_PREFERENCES_FILE_KEY = "com.np.fitnessapp.APP_PREFERENCES";
    public static final String SAVED_USERID_KEY = "savedUserIdKey";

    private User user;

    private FitnessApp() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
