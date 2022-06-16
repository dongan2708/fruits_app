package com.android.appfruit.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.MainActivity;
import com.android.appfruit.R;
import com.android.appfruit.adapter.CategoryAdapter;
import com.android.appfruit.entity.Category;
import com.android.appfruit.service.CategoryService;
import com.android.appfruit.util.RetrofitGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private CategoryService categoryService;
    private View view;
    private List<Category> categories;
    private Context currentContext;
    private RecyclerView recyclerView;

    private String token = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentContext = container.getContext();
        view = inflater.inflate(R.layout.fragment_category, container, false);
        initData(); // kéo thông tin categories từ api.
        initView(); // tạo recycler view, add categories vào recycler view.
        return view;
    }

    private void initView() {
        // khởi tạo recycler view
        recyclerView = view.findViewById(R.id.recycler_view_list_category);
        // set layout default
        recyclerView.setLayoutManager(new LinearLayoutManager(currentContext));
        // set adapter kèm dữ liệu
        recyclerView.setAdapter(new CategoryAdapter(currentContext, categories));

    }

    private void initData() {
        // cấu hình để call api.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // khởi tạo categories
        categories = new ArrayList<>();
        SharedPreferences settings = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = settings.getString("token", "");
        String refreshToken = settings.getString("refreshToken", "");
        Log.d("refreshToken", refreshToken);
        // khởi tạo retrofit để call api trường hợp chưa.
        if (categoryService == null) {
            categoryService = RetrofitGenerator.createService(CategoryService.class,token);
        }
        try {
            Response<List<Category>> CategoryResponse = categoryService.getAll().execute();
            // trường hợp thành công.
            if (CategoryResponse.isSuccessful()) {
                // thì lấy danh sách categories.
                categories = CategoryResponse.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Category fragment", e.getMessage());
        }
    }
}
