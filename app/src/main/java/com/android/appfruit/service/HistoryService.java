package com.android.appfruit.service;

import com.android.appfruit.entity.ShoppingCart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HistoryService {
    @GET("api/v1/orders/orderHistory")
    Call<List<ShoppingCart>> getAll();
}
