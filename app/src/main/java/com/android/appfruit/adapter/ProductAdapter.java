package com.android.appfruit.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.R;
import com.android.appfruit.dto.AddCartDto;
import com.android.appfruit.entity.Product;
import com.android.appfruit.fragment.ShoppingCartFragment;
import com.android.appfruit.service.CartService;
import com.android.appfruit.util.RetrofitGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable{

    List<Product> productList;
    Context mContext;
    private boolean isLoadingAdd;
    CartService cartService;
    private String token = null;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public ProductAdapter(Context context) {
        this.productList = new ArrayList<>();
        this.mContext = context;
    }
    public ProductAdapter(Context context, List<Product> products) {
        this.productList = products;
        this.mContext = context;
    }

    public ProductAdapter(Context applicationContext, int i, ArrayList<Product> filteredShapes) {
    }

    public void setData(List<Product> data){
        this.productList = data;

        SharedPreferences settings = mContext.getSharedPreferences("token", Context.MODE_PRIVATE);
        token = settings.getString("token", "");

        this.cartService = RetrofitGenerator.createService(CartService.class, token);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruits_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            return new ViewHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder.getItemViewType() == VIEW_TYPE_ITEM){
            Product fruit = productList.get(position);
            holder.fruitName.setText(fruit.getName());
            holder.fruitPrice.setText(String.valueOf(fruit.getPrice()));
            try {
                Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().override(70, 70)).load(fruit.getThumbnail()).into(holder.imagView);
            }catch (Exception ex){
                Log.d("Error", String.format("Cant load image from product id %d, image link %s", fruit.getId(), fruit.getThumbnail()));
            }
            holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Add to cart
                    ShoppingCartFragment.currentProduct = productList.get(position);
                    try {
                        cartService.addToCart(new AddCartDto(ShoppingCartFragment.currentProduct.getId(), 1)).execute();
                        Toast.makeText(view.getContext(), "Add Success",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("Add +1", String.valueOf(ShoppingCartFragment.currentProduct));
    
                    // Redirect product to cart
    //                ((FragmentActivity) view.getContext()).getSupportFragmentManager()
    //                        .beginTransaction()
    //                        .replace(R.id.frame_layout, MainActivity.shoppingCartFragment, ShoppingCartFragment.class.getName())
    //                        .commit();
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(productList != null && productList.size() - 1 == position && isLoadingAdd){
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }
    @Override
    public Filter getFilter() {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagView;
        public TextView fruitName, fruitPrice;
        public ImageButton imgAddToCart;

        public ViewHolder(View itemView) {
            super(itemView);
            imagView = itemView.findViewById(R.id.imgView);
            fruitName = itemView.findViewById(R.id.txtView);
            fruitPrice = itemView.findViewById(R.id.txt1);
            imgAddToCart = itemView.findViewById(R.id.img_add_to_cart);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
    public void addFooterLoading(){
        isLoadingAdd = true;
        productList.add(new Product());
    }

    public void removeFooterLoading(){
        isLoadingAdd = false;
        int position = productList.size() - 1;
        Product p = productList.get(position);
        if(p != null){
            productList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
