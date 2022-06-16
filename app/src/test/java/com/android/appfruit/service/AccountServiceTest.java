package com.android.appfruit.service;

import com.android.appfruit.dto.LoginDto;
import com.android.appfruit.dto.LoginToken;
import com.android.appfruit.entity.Account;
import com.android.appfruit.util.RetrofitGenerator;
import com.google.gson.Gson;


import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.http.PUT;

public class AccountServiceTest {

    AccountService accountService;
    MyProfileService profileService;
    @Before
    public void setUp() throws Exception {
        accountService = RetrofitGenerator.createService(AccountService.class);
    }

    @Test
    public void register() {

        Account account = new Account();
        account.setUsername("Xuan Hung");
        account.setName("Dao");
        account.setPassword("1234");
        account.setPhone("0912332");

        account.setPassword("1234");
        try {
            Account savedAccount = accountService.register(account).execute().body();
            System.out.println(savedAccount.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testRegister() {
        LoginDto login = new LoginDto();
        login.setUsername("test2");
        login.setPassword("1234");
        try {
            Response<LoginToken> credentialDtoResponse = accountService.login(login).execute();
            System.out.println(credentialDtoResponse.code());
            System.out.println(credentialDtoResponse.isSuccessful());
            System.out.println(credentialDtoResponse.toString());
            System.out.println(new Gson().toJson(credentialDtoResponse) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @Test
//    public void testProfile(){
//        Account account = new Account();
//        account.getUsername();
//        account.getName();
//        account.getPhone();
//        account.getAddress();
//        try {
//            Response<List<Account>> credentialDtoResponse = profileService.getAll().execute();
//            System.out.println(credentialDtoResponse.code());
//            System.out.println(credentialDtoResponse.isSuccessful());
//            System.out.println(credentialDtoResponse.toString());
//            System.out.println(new Gson().toJson(credentialDtoResponse) );
//
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//    }
}