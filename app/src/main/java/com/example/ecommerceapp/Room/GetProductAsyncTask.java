package com.example.ecommerceapp.Room;

import android.os.AsyncTask;

import com.example.ecommerceapp.modela.ProductModel;

import java.util.List;

public class GetProductAsyncTask extends AsyncTask<Void,Void, List<ProductModel>> {
    public GetProductAsyncTask(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    private ProductDAO productDAO;


    @Override
    protected List<ProductModel> doInBackground(Void... voids) {
        return productDAO.getAllProducts();
    }
}
