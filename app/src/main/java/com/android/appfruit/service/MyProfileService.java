package com.android.appfruit.service;

import com.android.appfruit.entity.Account;
import com.android.appfruit.entity.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MyProfileService {
    @GET("api/v1/accounts/profile")
    Call<Account> getAll();
}
