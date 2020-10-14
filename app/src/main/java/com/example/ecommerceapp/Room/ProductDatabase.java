package com.example.ecommerceapp.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ecommerceapp.modela.ProductModel;

@Database(entities = {ProductModel.class},version = 2,exportSchema = false)

public abstract class ProductDatabase extends RoomDatabase {



    public abstract ProductDAO getProductDAO();

}
