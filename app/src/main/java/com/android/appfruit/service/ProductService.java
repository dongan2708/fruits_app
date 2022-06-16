package com.android.appfruit.service;


import com.android.appfruit.entity.ListProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {
    @GET("/api/v1/products/search/{name}")
    Call<ListProductResponse> getUserData(@Query("search") String username);
    @GET("api/v1/products/list")
    Call<ListProductResponse> getAll(@Query("page") Integer page, @Query("pageSize") Integer limit, @Query("categoryId") Integer id);

    @GET("api/v1/products/list")
    Call<ListProductResponse> getByCategory(@Query("categoryId") int id);
}
