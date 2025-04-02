package com.example.composedemo.data

import com.example.composedemo.model.MainContent
import com.example.composedemo.model.Product
import org.json.JSONArray
import retrofit2.Response

class Repository {
    private val apiService = RetrofitInstance.api

    suspend fun getProducts(): List<Product> {
        return apiService.getProducts()
    }

    suspend fun getBanners(banner: JSONArray): Response<MainContent.ApiResponse> {
        return apiService.getBanners(banner)
    }
}