
package com.android.appfruit.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.R;
import com.android.appfruit.adapter.PaginationScrollListener;
import com.android.appfruit.adapter.ProductAdapter;
import com.android.appfruit.entity.ListProductResponse;
import com.android.appfruit.entity.Product;
import com.android.appfruit.service.ProductService;
import com.android.appfruit.util.RetrofitGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ProductFragment extends Fragment {
    public static ArrayList<Product> productList = new ArrayList<Product>();
    private int currentCategoryId = 0;
    private ProductService productService;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private View view;
    private List<Product> products;
    private Context currentContext;
    private boolean isLoading;
    private boolean isLastPage;
    private int totalPage = 5;
    private int currentPage = 1;
    private int limit = 5;
    private String token = null;

    private void setFirstData() {
        products = getProducts();
        productAdapter.setData(products);
        if (currentPage < totalPage) {
            productAdapter.addFooterLoading();
        } else {
            isLastPage = true;
        }
    }
    private List<Product> getProducts() {
        Toast.makeText(currentContext, "Current page: " + currentPage, Toast.LENGTH_SHORT).show();
        try {
            Response<ListProductResponse> responseProductResponse = null;
            Log.d("Logger", "currentCategoryId..." + currentCategoryId);
            if (currentCategoryId == 0) {
                responseProductResponse = productService.getAll(currentPage, limit, null).execute();
            } else {
                responseProductResponse = productService.getAll(currentPage, limit, currentCategoryId).execute();
            }
            Log.d("Logger", "success..." + responseProductResponse.isSuccessful());
            if (responseProductResponse.isSuccessful()) {
                Log.d("Size", "" + responseProductResponse.body().getData().size());
                try {
                    Log.d("Pagination", responseProductResponse.body().getPagination().toString());
                    ListProductResponse listProductResponse = responseProductResponse.body();
                    currentPage = listProductResponse.getPagination().getPage();
                    totalPage = listProductResponse.getPagination().getTotalPages();
                    return listProductResponse.getData();
                } catch (Exception ex) {
                    currentPage = 1;
                    Log.d("Error", ex.getMessage());
                }
            } else {
                Log.d("Logger", "reason..." + responseProductResponse.message());
                Log.d("Logger", "reason..." + responseProductResponse.errorBody());
                Log.d("Logger", "reason..." + responseProductResponse.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentContext = container.getContext();
        view = inflater.inflate(R.layout.fragment_product, container, false);
        config();
        initView();
        setFirstData();
        return view;
    }
    private void initView() {

        recyclerView = view.findViewById(R.id.recycler_view_list_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(currentContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(currentContext);
        recyclerView.setAdapter(productAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(currentContext, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void loadMoreItems() {
                if(!isLoading){
                    isLoading = true;
                    currentPage += 1;
                    loadNextPage();
                }
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
    }

    private void loadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Product> list = getProducts();
                productAdapter.removeFooterLoading();
                products.addAll(list);
                productAdapter.notifyDataSetChanged();
                isLoading = false;
                if (currentPage < totalPage) {
                    productAdapter.addFooterLoading();
                } else {
                    isLastPage = true;
                }
            }
        }, 2000);
    }

    private void config() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        products = new ArrayList<>();
        SharedPreferences settings = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = settings.getString("token", "");
        String refreshToken = settings.getString("refreshToken", "");
        Log.d("token", token);
        Log.d("refreshToken", refreshToken);
        if (productService == null) {
            productService = RetrofitGenerator.createService(ProductService.class, token);
        }
    }

    public int getCurrentCategoryId() {
        return currentCategoryId;
    }

    public void setCurrentCategoryId(int currentCategoryId) {
        this.currentCategoryId = currentCategoryId;
    }

}