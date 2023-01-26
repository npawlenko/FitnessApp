package com.np.fitnessapp.api.food;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsContainer {
    @SerializedName("count")
    public int count;
    @SerializedName("products")
    public List<Product> products;
}
