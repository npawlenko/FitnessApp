package com.np.fitnessapp.api.exercises;

import com.google.gson.annotations.SerializedName;

public class ApiExercise {
    @SerializedName("name")
    public String name;
    @SerializedName("calories_per_hour")
    public String calories;
    @SerializedName("duration_minutes")
    public String duration;
    @SerializedName("total_calories")
    public String totalCalories;
}
