package com.example.composedemo.data

import com.example.composedemo.model.MainContent.ApiResponse
import com.example.composedemo.model.Product
import org.json.JSONArray
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("No-Authentication: true", "Cache-Control: no-cache")
    @GET("cms/content?platformId=5")
    suspend fun getBanners(@Query("contentKeys") contentKeys: JSONArray): Response<ApiResponse>
}