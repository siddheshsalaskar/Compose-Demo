package com.example.composedemo.data

import com.example.composedemo.model.MainContent
import org.json.JSONArray
import retrofit2.Response

class Repository {
    private val apiService = RetrofitInstance.api

    suspend fun getBanners(banner: JSONArray): Response<MainContent.ApiResponse> {
        return apiService.getBanners(banner)
    }
}