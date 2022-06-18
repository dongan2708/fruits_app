package com.android.appfruit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.MainActivity;
import com.android.appfruit.R;
import com.android.appfruit.entity.CartItem;
import com.android.appfruit.entity.ShoppingCart;
import com.android.appfruit.fragment.DetailsHistoryFragment;
import com.android.appfruit.fragment.ProductFragment;
import com.android.appfruit.service.HistoryService;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>{
    List<Integer> listImage;
    List<ShoppingCart> shoppingCarts;
    Context mContext;
    int currentImageIndex = 0;


    public OrderHistoryAdapter(Context context) {
        this.shoppingCarts = new ArrayList<>();
        this.mContext = context;
    }
    public OrderHistoryAdapter(Context context, List<ShoppingCart> cartItemList) {
        this.shoppingCarts = cartItemList;
        this.mContext = context;
        this.listImage = new ArrayList<>();

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
        ShoppingCart shoppingCart = shoppingCarts.get(position);
        holder.name.setText(shoppingCart.getShipName());
        holder.price.setText(String.valueOf(shoppingCart.getTotalPrice()));
        holder.date.setText(String.valueOf(shoppingCart.getCreatedAt()));
//        holder.status.setText(shoppingCart.getStatus());
        holder.address.setText(shoppingCart.getShipAddress());
        holder.phone.setText(shoppingCart.getShipPhone());
//        holder.linearLayout.setBackgroundResource(listImage.get(currentImageIndex));
//        currentImageIndex++;
//        if (currentImageIndex >= listImage.size()) {
//            currentImageIndex = 0;
//        }
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.detailsHistoryFragment.setCurrentHistoryId(shoppingCart.getId());
                ((FragmentActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, MainActivity.detailsHistoryFragment, DetailsHistoryFragment.class.getName())
                        .commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return shoppingCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView name,price,date,address,phone,status;
        public Button btnDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            btnDetails = itemView.findViewById(R.id.btn_chi_tiet);
            name = itemView.findViewById(R.id.orderName);
            date = itemView.findViewById(R.id.orderDate);
            price = itemView.findViewById(R.id.orderPrice);
            status = itemView.findViewById(R.id.orderStatus);
            address = itemView.findViewById(R.id.orderAddress);
            phone = itemView.findViewById(R.id.orderPhone);

        }
    }
}
