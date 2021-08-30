package com.example.myroomdb.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(p:Product)

    @Query("select * from product_table")
    fun getAll() : List<Product>

    @Query("select * from product_table where price < :priceRange")
    fun getPriceLessThan(priceRange:Double) : List<Product>


}