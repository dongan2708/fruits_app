package com.android.appfruit.service;

import com.android.appfruit.dto.CredentialDto;

import com.android.appfruit.dto.LoginDto;
import com.android.appfruit.dto.LoginToken;
import com.android.appfruit.entity.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountService {
    @POST("/api/v1/accounts/register")
    Call<Account> register(@Body Account account);

    @POST("/api/v1/accounts/login")
    Call<LoginToken> login(@Body LoginDto loginDto);
}
