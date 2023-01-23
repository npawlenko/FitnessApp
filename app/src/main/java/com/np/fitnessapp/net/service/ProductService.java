package com.np.fitnessapp.net.service;

import com.np.fitnessapp.net.ProductContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    @GET("product/{barcode}?fields=_id,product_name,nutriments,ingredients,nutrition_grades,image_url")
    Call<ProductContainer> getProductByBarcode(@Path("barcode") String barcode);

    @GET("search?categories_tags_pl={q}&fields=_id,product_name,nutriments,ingredients,nutrition_grades,image_url")
    Call<ProductContainer> searchForProduct(@Path("q") String q);
}
