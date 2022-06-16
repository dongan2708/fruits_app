package com.android.appfruit.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.appfruit.MainActivity;
import com.android.appfruit.R;
import com.android.appfruit.activity.LoginActivity;
import com.android.appfruit.entity.Account;
import com.android.appfruit.entity.Category;
import com.android.appfruit.service.CategoryService;
import com.android.appfruit.service.MyProfileService;
import com.android.appfruit.util.RetrofitGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private MyProfileService myProfileService;
    private View view;
    private String token = null;
    private TextView txtName,txtUsername,txtPhone,txtAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Button btnLogout = view.findViewById(R.id.btnLogOut);
         txtName = view.findViewById(R.id.profileName);
         txtUsername = view.findViewById(R.id.profileUsername);
         txtPhone = view.findViewById(R.id.profilePhone);
         txtAddress = view.findViewById(R.id.profileAddress);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);            }
        });
        initData(); // kéo thông tin categories từ api.
        return view;
    }
    private void initData() {
        // cấu hình để call api.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences settings = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = settings.getString("token", "");
        String refreshToken = settings.getString("refreshToken", "");
        Log.d("refreshToken", refreshToken);
        // khởi tạo retrofit để call api trường hợp chưa.
        if (myProfileService == null) {
            myProfileService = RetrofitGenerator.createService(MyProfileService.class,token);
        }
        try {
            Response<Account> AccountResponse = myProfileService.getAll().execute();
            // trường hợp thành công.
            if (AccountResponse.isSuccessful()) {

                Account account = AccountResponse.body();
                txtName.setText(account.getName());
                Log.d("name: ", String.valueOf(account.getName()));
                txtUsername.setText(account.getUsername());
                txtPhone.setText(account.getPhone());
                txtAddress.setText(account.getAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", e.getMessage());
        }
    }
}