package com.example.ecommerceapp.Room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecommerceapp.modela.ProductModel;

import java.util.List;

@Dao
public interface ProductDAO {


    @Insert
    void insertProduct(ProductModel productModel);

    @Query("SELECT*FROM products")
    List<ProductModel> getAllProducts();

    @Query("DELETE FROM products")
    void deleteAllProducts();
    @Update
    void UpdateProduct(ProductModel productModel);



}
