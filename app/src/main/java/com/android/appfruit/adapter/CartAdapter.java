package com.android.appfruit.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.MainActivity;
import com.android.appfruit.R;

import com.android.appfruit.entity.CartItem;
import com.android.appfruit.entity.Product;
import com.android.appfruit.fragment.ProductFragment;
import com.android.appfruit.fragment.ShoppingCartFragment;
import com.android.appfruit.service.CartService;
import com.android.appfruit.util.RetrofitGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<CartItem> itemList;
    Context mContext;
    CartService cartService;
    private String token = null;
    ShoppingCartFragment shoppingCartFragment;

    public CartAdapter(Context context, List<CartItem> items) {
        this.mContext = context;
        this.itemList = items;

        SharedPreferences settings = mContext.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = settings.getString("token", "");

        this.cartService = RetrofitGenerator.createService(CartService.class, token);
        notifyDataSetChanged();
    }

    public void setShoppingCartFragment(ShoppingCartFragment shoppingCartFragment) {
        this.shoppingCartFragment = shoppingCartFragment;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CartItem item = itemList.get(position);
            holder.fruitName.setText(item.getProductName());
            holder.fruitPrice.setText(String.valueOf(item.getUnitPrice()));
            Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().override(70, 70)).load(item.getThumbnailProduct()).into(holder.imagView);

        Fragment currentFragment = ((AppCompatActivity)mContext).getSupportFragmentManager().findFragmentByTag(ShoppingCartFragment.TAG);
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction();

            holder.removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Remove item from cart
                    ShoppingCartFragment.currentItem = itemList.get(position);
                    try {
                        cartService.remove(ShoppingCartFragment.currentItem.getProductId()).execute();
                        itemList = cartService.getCart().execute().body().getOrderDetails();
                        notifyDataSetChanged();
                        shoppingCartFragment.items = itemList;
                        shoppingCartFragment.cartPrice.setText(shoppingCartFragment.calculateTotal(itemList) + "");
                        Toast.makeText(view.getContext(), "Remove Success", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("-1", String.valueOf(ShoppingCartFragment.currentItem));

                   // fragmentTransaction.detach(currentFragment);
                    //fragmentTransaction.attach(currentFragment);
                    //fragmentTransaction.commit();
                    Log.d("Refresh: " , "Success");
                }
            });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagView;
        public TextView fruitName, fruitPrice;
        public ImageButton removeItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagView = itemView.findViewById(R.id.imgView1);
            fruitName = itemView.findViewById(R.id.txtView1);
            fruitPrice = itemView.findViewById(R.id.txt11);
            removeItem = itemView.findViewById(R.id.remove_item);
        }
    }

}
