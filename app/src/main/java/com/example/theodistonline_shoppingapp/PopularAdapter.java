package com.example.theodistonline_shoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private List<PopularDomain> list;
    private Context context;
    private OnCartChangedListener cartChangedListener;

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    public PopularAdapter(List<PopularDomain> list, Context context, OnCartChangedListener listener) {
        this.list = list;
        this.context = context;
        this.cartChangedListener = listener;
    }

    public void updateList(List<PopularDomain> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_pup_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PopularDomain item = list.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvPrice.setText(String.format(Locale.getDefault(), "K%.2f", item.getPrice()));
        holder.tvRating.setText(String.format(Locale.getDefault(), "%.1f", item.getRating()));
        holder.tvCategory.setText(item.getCategory());
        holder.ivProduct.setImageResource(item.getImageResource());

        // Helper conversion: PopularDomain to Product
        Product product = convertToProduct(item);

        holder.btnAdd.setOnClickListener(v -> {
            CartManager.getCart().addProduct(product);
            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();

            if (cartChangedListener != null) {
                cartChangedListener.onCartChanged();
            }
        });
    }

    private Product convertToProduct(PopularDomain item) {
        String cat = item.getCategory();
        if ("Stationary".equalsIgnoreCase(cat)) {
            return new Stationary(item.getTitle(), item.getPrice(), item.getImageResource());
        } else if ("Technology".equalsIgnoreCase(cat) || "Electronics".equalsIgnoreCase(cat)) {
            return new Technology(item.getTitle(), item.getPrice(), item.getImageResource());
        } else if ("Toys".equalsIgnoreCase(cat)) {
            return new Toys(item.getTitle(), item.getPrice(), item.getImageResource());
        } else if ("Office Furniture".equalsIgnoreCase(cat) || "Furniture".equalsIgnoreCase(cat)) {
            return new OfficeFurniture(item.getTitle(), item.getPrice(), item.getImageResource());
        } else {
            return new Product(item.getTitle(), item.getPrice(), cat, item.getImageResource());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        TextView tvTitle, tvRating, tvPrice, tvCategory;
        Button btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
