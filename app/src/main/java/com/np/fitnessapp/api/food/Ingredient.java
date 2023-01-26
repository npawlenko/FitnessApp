package com.np.fitnessapp.api.food;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("text")
    public String text;
    @SerializedName("percent_estimate")
    public double percentEstimate;
    @SerializedName("percent_max")
    public double percentMax;
    @SerializedName("percent_min")
    public double percentMin;
    @SerializedName("vegan")
    public String vegan;
    @SerializedName("vegetarian")
    public String vegetarian;
}
