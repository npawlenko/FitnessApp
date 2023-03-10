package com.np.fitnessapp.api.food;

import com.google.gson.annotations.SerializedName;

public class ProductContainer {
    @SerializedName("code")
    public long code;
    @SerializedName("product")
    public Product product;
    @SerializedName("status")
    public int status;
    @SerializedName("statusVerbose")
    public String statusVerbose;
}
