package com.example.ecommerceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.modela.ProductModel;

import java.util.List;

public class ProductAdabter extends RecyclerView.Adapter<ProductAdabter.ProductViewHolder> {
 private List<ProductModel>prodctsList;
 private Context context;
 private OnProductClickListener onProductClickListener;
 private onAddProductClickListener onAddProductClickListener;

 public interface OnProductClickListener{
     void onProductClick(View view,int position);
 }



    public interface onAddProductClickListener{
        void onAddProductClick(View view,int position);
    }


    public ProductAdabter(List<ProductModel> prodctsList, Context context, OnProductClickListener onProductClickListener, ProductAdabter.onAddProductClickListener onAddProductClickListener) {
        this.prodctsList = prodctsList;
        this.context = context;
        this.onProductClickListener = onProductClickListener;
        this.onAddProductClickListener = onAddProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.product_rv_item,parent,false);
        return new  ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        ProductModel productModel=prodctsList.get(position);
        holder.productTitleTv.setText(productModel.getTitle());
        holder.productDetialsTv.setText(productModel.getDetails());
        holder.productPriceTv.setText(productModel.getPrice());
        Glide.with(context).load(productModel.getImage()).into(holder.productIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProductClickListener.onProductClick(view,holder.getAdapterPosition());
            }
        });



        holder.addProductIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddProductClickListener.onAddProductClick(view,holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return prodctsList.size();
    }

    class  ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productIv;
        TextView productTitleTv;
        TextView productDetialsTv;
        TextView productPriceTv;
        ImageButton addProductIb;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productIv=itemView.findViewById(R.id.product_iv);
            productTitleTv=itemView.findViewById(R.id.product_title_tv);
            productDetialsTv=itemView.findViewById(R.id.product_details_tv);
            productPriceTv=itemView.findViewById(R.id.product_price_tv);
            addProductIb=itemView.findViewById(R.id.add_product_ib);
        }
    }
}



