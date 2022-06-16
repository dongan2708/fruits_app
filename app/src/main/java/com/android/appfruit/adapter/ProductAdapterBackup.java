package com.android.appfruit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.R;
import com.android.appfruit.entity.Product;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProductAdapterBackup extends RecyclerView.Adapter<ProductAdapterBackup.ViewHolder>
{
    List<Product> productList;
    Context mContext;

    public ProductAdapterBackup(Context context, List<Product> products)
    {
        this.productList = products;
        this.mContext = context;
    }
    @NonNull
    @Override
    public ProductAdapterBackup.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fruits_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product fruit = productList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitPrice.setText(String.valueOf(fruit.getPrice()));
        Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().override(70,70)).load(fruit.getThumbnail()).into(holder.imagView);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagView;
        public TextView fruitName, fruitPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            imagView = itemView.findViewById(R.id.imgView);
            fruitName = itemView.findViewById(R.id.txtView);
            fruitPrice = itemView.findViewById(R.id.txt1);
        }
    }
}
