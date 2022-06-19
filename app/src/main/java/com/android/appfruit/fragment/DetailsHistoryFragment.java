package com.android.appfruit.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.R;
import com.android.appfruit.adapter.HistoryDetailsAdapter;
import com.android.appfruit.entity.CartItem;
import com.android.appfruit.entity.ShoppingCart;
import com.android.appfruit.service.HistoryService;
import com.android.appfruit.util.RetrofitGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class DetailsHistoryFragment extends Fragment {
    private HistoryService historyService;
    private RecyclerView recyclerView;
    private int currentHistoryId = 0;
    private String token = null;
    private View view;
    private Context currentContext;
    private List<CartItem> cartItems;
    List<ShoppingCart> shoppingCarts;

    private HistoryDetailsAdapter historyDetailsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_details, container, false);
        config();
        initData();
        initView();
        setFirstData();
        return view;
    }
    private void initView() {
        // khởi tạo recycler view
        recyclerView = view.findViewById(R.id.details_history);
        // set layout default
        recyclerView.setLayoutManager(new LinearLayoutManager(currentContext));
        // set adapter kèm dữ liệu
        historyDetailsAdapter = new HistoryDetailsAdapter(currentContext);
        recyclerView.setAdapter(historyDetailsAdapter);
    }
    private List<CartItem> initData() {
        try {
            Response<List<ShoppingCart>> OrderDetailsResponse = historyService.getAll().execute();
            // trường hợp thành công.
            if (OrderDetailsResponse.isSuccessful()) {
                shoppingCarts = OrderDetailsResponse.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", e.getMessage());
        }
        return new ArrayList<>();
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
    private void setFirstData(){
        cartItems = initData();
        historyDetailsAdapter.setOrderDetails(cartItems);

    }

    public void setCurrentHistoryId(int currentHistoryId) {
        this.currentHistoryId = currentHistoryId;
    }
}