package com.np.fitnessapp.api.service;

import com.np.fitnessapp.api.food.ProductContainer;
import com.np.fitnessapp.api.food.ProductsContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("product/{barcode}?fields=_id,product_name,nutriments,ingredients,nutrition_grades,image_url")
    Call<ProductContainer> getProductByBarcode(@Path("barcode") String barcode);

    @GET("search?fields=_id,product_name,nutriments,ingredients,nutrition_grades,image_url")
    Call<ProductsContainer> searchForProduct(@Query("q") String q);
}
