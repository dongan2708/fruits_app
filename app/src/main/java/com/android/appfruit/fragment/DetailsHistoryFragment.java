package com.android.appfruit.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.R;
import com.android.appfruit.activity.LoginActivity;
import com.android.appfruit.adapter.OrderHistoryAdapter;
import com.android.appfruit.entity.Account;
import com.android.appfruit.entity.CartItem;
import com.android.appfruit.entity.ShoppingCart;
import com.android.appfruit.service.HistoryService;
import com.android.appfruit.service.MyProfileService;
import com.android.appfruit.util.RetrofitGenerator;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class DetailsHistoryFragment extends Fragment {
    private HistoryService historyService;
    private RecyclerView recyclerView;
    private String token = null;
    private View view;
    private Context currentContext;
    List<ShoppingCart> cartItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        config();
        initData();
        initView();
        return view;
    }
    private void initView() {
        // khởi tạo recycler view
        recyclerView = view.findViewById(R.id.details_history);
        // set layout default
        recyclerView.setLayoutManager(new LinearLayoutManager(currentContext));
        // set adapter kèm dữ liệu
//        recyclerView.setAdapter(new DetailsHistoryFragment(currentContext, cartItems));
    }
    private void initData() {
        try {
            Response<List<ShoppingCart>> OrderDetailsResponse = historyService.getAll().execute();
            // trường hợp thành công.
            if (OrderDetailsResponse.isSuccessful()) {
                cartItems = OrderDetailsResponse.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", e.getMessage());
        }
    }
    private void config(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences settings = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = settings.getString("token", "");
        String refreshToken = settings.getString("refreshToken", "");
        Log.d("refreshToken", refreshToken);
        // khởi tạo retrofit để call api trường hợp chưa.
        if (historyService == null) {
            historyService = RetrofitGenerator.createService(HistoryService.class,token);
        }
    }
}