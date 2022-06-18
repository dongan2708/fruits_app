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
import com.android.appfruit.service.HistoryService;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailsAdapter extends RecyclerView.Adapter<HistoryDetailsAdapter.ViewHolder>{

    List<CartItem> cartItems;
    Context mContext;

    public HistoryDetailsAdapter(Context context) {
        this.cartItems = new ArrayList<>();
        this.mContext = context;
    }
    public HistoryDetailsAdapter(Context context, List<CartItem> cartItemList) {
        this.cartItems = cartItemList;
        this.mContext = context;
    }
    public void setOrderDetails(List<CartItem> orderDetails){

        this.cartItems = orderDetails;
    }
    @NonNull
    @Override
    public HistoryDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem fruit = cartItems.get(position);
        holder.name.setText(fruit.getProductName());
        holder.price.setText(String.valueOf(fruit.getUnitPrice()));
        holder.quantity.setText(fruit.getQuantity());
        holder.orderId.setText(fruit.getOrderId());
        holder.productId.setText(fruit.getProductId());
    }
    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagView;
        public TextView name, price, quantity,orderId,productId;

        public ViewHolder(View itemView) {
            super(itemView);
            imagView = itemView.findViewById(R.id.thumbnailProduct);
            name = itemView.findViewById(R.id.textName);
            price = itemView.findViewById(R.id.textPrice);
            quantity = itemView.findViewById(R.id.textQuantity);
            orderId = itemView.findViewById(R.id.txtOrderId);
            productId = itemView.findViewById(R.id.textPrice);

        }
    }
}
