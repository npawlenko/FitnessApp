package com.np.fitnessapp.api.service;

import com.np.fitnessapp.api.exercises.ApiExercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExerciseService {
    @GET("caloriesburned")
    Call<List<ApiExercise>> searchForExercise(@Query("activity") String activity);
}
