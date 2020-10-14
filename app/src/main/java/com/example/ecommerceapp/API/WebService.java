package com.example.ecommerceapp.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {


    @GET("products")
    Call<ProductsResponse> getProducts();
}
