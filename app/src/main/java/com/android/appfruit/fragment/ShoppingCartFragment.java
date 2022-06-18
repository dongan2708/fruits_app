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
import android.widget.TextView;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.MainActivity;
import com.android.appfruit.R;
import com.android.appfruit.adapter.CartAdapter;
import com.android.appfruit.entity.CartItem;
import com.android.appfruit.entity.Product;
import com.android.appfruit.entity.ShoppingCart;
import com.android.appfruit.service.CartService;
import com.android.appfruit.util.RetrofitGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ShoppingCartFragment extends Fragment {

    public static final String TAG = ShoppingCartFragment.class.getName();
    private static ShoppingCartFragment instance = null;

    private View view;
    private Context currentContext;
    private static RecyclerView recyclerView;
    public static Product currentProduct;
    public static CartItem currentItem;
    public List<CartItem> items;
    private CartService cartService;
    private String token = null;
    public TextView cartPrice;


    public ShoppingCartFragment() {
        items = new ArrayList<>();
    }

    public double calculateTotal(List<CartItem> items) {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getUnitPrice() * items.get(i).getQuantity();
        }
        Log.d("Total: ", String.valueOf(total));
        return total;
    }

    public ShoppingCart initData() {
        try {
            Response<ShoppingCart> CartItemResponse = null;
            CartItemResponse = cartService.getCart().execute();
            if (CartItemResponse.isSuccessful()) {
                items = CartItemResponse.body().getOrderDetails();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Cart fragment", e.getMessage());
            items = new ArrayList<>();
        }
        return new ShoppingCart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentContext = container.getContext();
        view = inflater.inflate(R.layout.fragment_cart, container,false);
        instance = this;
        cartPrice = view.findViewById(R.id.total_price);
        Button conShoppingCart = view.findViewById(R.id.con_shopping_cart);
        Button checkOutCart = view.findViewById(R.id.check_out_cart);

        conShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, MainActivity.productFragment, ProductFragment.class.getName())
                        .commit();
                Toast.makeText(view.getContext(), "Redirect Success",Toast.LENGTH_SHORT).show();
            }
        });

        checkOutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((FragmentActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, MainActivity.checkOutFragment, CheckOutFragment.class.getName())
                        .commit();
                Toast.makeText(view.getContext(), "Redirect Success", Toast.LENGTH_SHORT).show();
            }
        });

        config();
        initData(); // request api
        // Calculate total price for items in cart
        double total = calculateTotal(items);
        cartPrice.setText(String.valueOf(total));
        Log.d("data", "received");
        initView();
        Log.d("success", "success");

        return view;
    }

    private void config() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences settings = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = settings.getString("token", "");
        String refreshToken = settings.getString("refreshToken", "");
        Log.d("token", token);
        Log.d("refreshToken", refreshToken);
        if (cartService == null) {
            cartService = RetrofitGenerator.createService(CartService.class,token);
        }
    }

    private void initView(){
        recyclerView = view.findViewById(R.id.recycler_view_list_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(currentContext));
        CartAdapter cartAdapter = new CartAdapter(currentContext, items);
        recyclerView.setAdapter(cartAdapter);
        cartAdapter.setShoppingCartFragment(this);
    }
}
