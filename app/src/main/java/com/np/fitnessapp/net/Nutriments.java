package com.np.fitnessapp.net;

import com.google.gson.annotations.SerializedName;

public class Nutriments {
    @SerializedName("energy-kcal")
    public double totalEnergy;
    @SerializedName("energy-kcal_100g")
    public double energy;
    @SerializedName("carbohydrates_100g")
    public double carbohydrates;
    @SerializedName("fat_100g")
    public double fat;
    @SerializedName("proteins_100g")
    public double proteins;
    @SerializedName("salt_100g")
    public double salt;
    @SerializedName("sugars_100g")
    public double sugars;
    @SerializedName("sodium_100g")
    public double sodium;
}
