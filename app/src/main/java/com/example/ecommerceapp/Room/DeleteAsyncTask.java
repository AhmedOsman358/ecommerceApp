package com.example.ecommerceapp.Room;

import android.os.AsyncTask;

public class DeleteAsyncTask extends AsyncTask<Void,Void,Void> {
    private ProductDAO productDAO;

    public DeleteAsyncTask(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        productDAO.deleteAllProducts();
        return null;
    }
}
