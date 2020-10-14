package com.example.ecommerceapp.Room;

import android.os.AsyncTask;

import com.example.ecommerceapp.modela.ProductModel;

public class UpdateAsyncTask extends AsyncTask<ProductModel,Void,Void> {
    private ProductDAO productDAO;

    public UpdateAsyncTask(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected Void doInBackground(ProductModel... productModels) {
        productDAO.UpdateProduct(productModels[0]);
        return null;
    }
}
