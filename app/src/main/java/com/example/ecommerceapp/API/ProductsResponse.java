package com.example.ecommerceapp.API;

import com.example.ecommerceapp.modela.ProductModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsResponse {
    @SerializedName("products")
    private List<ProductModel>productsList;
    public List<ProductModel> getProductsList(){
        return productsList;
    }

    public void setProductsList(List<ProductModel> productsList) {
        this.productsList = productsList;
    }
}
