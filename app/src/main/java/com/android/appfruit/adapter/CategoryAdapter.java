package com.android.appfruit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.appfruit.MainActivity;
import com.android.appfruit.R;

import com.android.appfruit.entity.Category;
import com.android.appfruit.entity.ListCategoryResponse;
import com.android.appfruit.entity.Product;
import com.android.appfruit.fragment.ProductFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    // dữ liệu hiển thị
    List<Integer> listImage;
    List<Category> categoryList;
    Context mContext;
    int currentImageIndex = 0;

    // khởi tạo
    public CategoryAdapter(Context context, List<Category> categories) {
        this.categoryList = categories;
        this.mContext = context;
        this.listImage = new ArrayList<>();
        listImage.add(R.drawable.wallpaper_springjpg);
        listImage.add(R.drawable.wallpaper_summer);
        listImage.add(R.drawable.wallpaper_fall1);
        listImage.add(R.drawable.wallpaper_winter);
    }
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // lấy thông tin hiển thị từ categories_item
        View shapeView = inflater.inflate(R.layout.categories_item, parent, false);
        return new CategoryAdapter.ViewHolder(shapeView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.txtName.setText(category.getName());
        holder.txtDescription.setText(category.getDescription());
        holder.linearLayout.setBackgroundResource(listImage.get(currentImageIndex));
        currentImageIndex++;
        if (currentImageIndex >= listImage.size()) {
            currentImageIndex = 0;
        }
        holder.btnViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.productFragment.setCurrentCategoryId(category.getId());
                ((AppCompatActivity) mContext).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, MainActivity.productFragment, ProductFragment.class.getName())
                        .commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView txtName, txtDescription;
        public Button btnViewMore;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDescription = itemView.findViewById(R.id.txt_description);
            linearLayout = itemView.findViewById(R.id.background_image);
            btnViewMore = itemView.findViewById(R.id.btn_view_more);
        }
    }

}
