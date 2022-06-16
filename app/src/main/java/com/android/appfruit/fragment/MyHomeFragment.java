package com.android.appfruit.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.appfruit.R;
import com.android.appfruit.ZoomOutPageTransformer;
import com.android.appfruit.adapter.PhotoViewPagerAdapter;
import com.android.appfruit.adapter.ProductAdapter;
import com.android.appfruit.entity.ListProductResponse;
import com.android.appfruit.entity.Photo;
import com.android.appfruit.entity.Product;
import com.android.appfruit.service.ProductService;
import com.android.appfruit.util.RetrofitGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Response;

public class MyHomeFragment extends Fragment {
    private ViewPager2 mViewPager2;
    private CircleIndicator3 mCircleIndicator3;
    private View view;
    private List<Photo> mListPhoto;

    private int currentCategoryId = 0;
    private ProductService productService;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> products;
    private Context currentContext;
    //
    private boolean isLoading;
    private boolean isLastPage;
    private int totalPage = 5;
    private int currentPage = 1;
    private String token = null;
    private int limit = 5;
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
        Toast.makeText(currentContext, "Load more data" + currentPage, Toast.LENGTH_SHORT).show();
        try {
            Response<ListProductResponse> responseProductResponse = null;
            if (currentCategoryId == 0) {
                responseProductResponse = productService.getAll(currentPage, limit, null).execute();
            } else {
                responseProductResponse = productService.getByCategory(currentCategoryId).execute();
            }
            if (responseProductResponse.isSuccessful()) {
                return responseProductResponse.body().getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    //xử lý và chạy sliderImage
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager2.getCurrentItem() == mListPhoto.size() -1){
                mViewPager2.setCurrentItem(0);
            }else {
                mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() +1);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        currentContext = container.getContext();

        mViewPager2 = view.findViewById(R.id.view_pager);
        mCircleIndicator3 = view.findViewById(R.id.circle_indicator);
        mListPhoto = getListPhoto();
        PhotoViewPagerAdapter adapter = new PhotoViewPagerAdapter(mListPhoto);
        mViewPager2.setAdapter(adapter);
        mCircleIndicator3.setViewPager(mViewPager2);
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 3000);
            }
        });
        mViewPager2.setPageTransformer(new ZoomOutPageTransformer());
//        initSearchWidgets();
        config();
        initView();
        // Inflate the layout for this fragment
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
            productService = RetrofitGenerator.createService(ProductService.class,token);
        }
    }

    public int getCurrentCategoryId() {
        return currentCategoryId;
    }

    public void setCurrentCategoryId(int currentCategoryId) {
        this.currentCategoryId = currentCategoryId;
    }
    //listImageView
    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.img1));
        list.add(new Photo(R.drawable.img4));
        list.add(new Photo(R.drawable.img3));
        list.add(new Photo(R.drawable.fruit));
        list.add(new Photo(R.drawable.img5));
        list.add(new Photo(R.drawable.img6));
        return list;
    }
    @Override
    public void onPause(){
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }
    @Override
    public void onResume(){
        super.onResume();
        mHandler.postDelayed(mRunnable,3000);
    }
//    private void initSearchWidgets() {
//        searchView = (SearchView) view.findViewById(R.id.ListSearchView);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s)
//            {
//                currentSearchText = s;
//                ArrayList<Product> filteredProducts = new ArrayList<Product>();
//
//                for(Product product: productArrayList)
//                {
//                    if(product.getName().toLowerCase().contains(s.toLowerCase()))
//                    {
//                        if(selectedFilter.equals("all"))
//                        {
//                            filteredProducts.add(product);
//                        }
//                        else
//                        {
//                            if(product.getName().toLowerCase().contains(selectedFilter))
//                            {
//                                filteredProducts.add(product);
//                            }
//                        }
//                    }
//                }
//                ProductAdapter adapter = new ProductAdapter(getApplicationContext(), 0, filteredProducts);
//                recyclerView.setAdapter(adapter);
//
//                return false;
//            }
//        });
//    }

}
