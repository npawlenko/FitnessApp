package com.np.fitnessapp.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("_id")
    public long id;
    @SerializedName("product_name")
    public String name;
    @SerializedName("image_url")
    public String imageUrl;
    @SerializedName("ingredients")
    public List<Ingredient> ingredients;
    @SerializedName("nutrition_grades")
    public String nutritionGrade;
    @SerializedName("nutriments")
    public Nutriments nutriments;
}
