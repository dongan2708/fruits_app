package com.android.appfruit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.R;
import com.android.appfruit.entity.CartItem;
import com.android.appfruit.entity.ShoppingCart;
import com.android.appfruit.service.HistoryService;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>{

    List<ShoppingCart> shoppingCarts;
    Context mContext;

    public OrderHistoryAdapter(Context context) {
        this.shoppingCarts = new ArrayList<>();
        this.mContext = context;
    }
    public OrderHistoryAdapter(Context context, List<ShoppingCart> cartItemList) {
        this.shoppingCarts = cartItemList;
        this.mContext = context;
    }
    @NonNull
    @Override
    public OrderHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_order_history, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShoppingCart fruit = shoppingCarts.get(position);
        holder.name.setText(fruit.getShipName());
        holder.price.setText(String.valueOf(fruit.getTotalPrice()));
        holder.date.setText(String.valueOf(fruit.getCreatedAt()));
        holder.address.setText(fruit.getShipAddress());
        holder.phone.setText(fruit.getShipPhone());
    }
    @Override
    public int getItemCount() {
        return shoppingCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,price,date,address,phone;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.orderName);
            date = itemView.findViewById(R.id.orderDate);
            price = itemView.findViewById(R.id.orderPrice);
            address = itemView.findViewById(R.id.orderAddress);
            phone = itemView.findViewById(R.id.orderPhone);

        }
    }
}
