package com.example.composedemo.data

import com.example.composedemo.model.Banner
import com.example.composedemo.model.Product
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("banners")
    suspend fun getBanners(): List<Banner>
}