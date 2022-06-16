package com.android.appfruit.service;

import com.android.appfruit.dto.AddCartDto;
import com.android.appfruit.dto.CheckOutDto;
import com.android.appfruit.entity.CartItem;

import com.android.appfruit.entity.ShoppingCart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CartService {
    @GET("api/v1/orders/cart1")
    Call<ShoppingCart> getCart();

    @POST("api/v1/orders/addToCart1")
    Call<CartItem> addToCart(@Body AddCartDto addCartDto);

    @POST("api/v1/orders/proceedOrder1")
    Call<ShoppingCart> checkOut(@Body CheckOutDto checkOutDto);
    @POST("api/v1/orders/remove1")
    Call<CartItem> remove(@Query("productId") int id);
}
