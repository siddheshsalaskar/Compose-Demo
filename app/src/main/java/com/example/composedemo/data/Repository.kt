package com.example.composedemo.data

import com.example.composedemo.model.Banner
import com.example.composedemo.model.Product

class Repository {
    private val apiService = RetrofitInstance.api

    suspend fun getProducts(): List<Product> {
        return apiService.getProducts()
    }

    suspend fun getBanners(): List<Banner> {
        return apiService.getBanners()
    }
}