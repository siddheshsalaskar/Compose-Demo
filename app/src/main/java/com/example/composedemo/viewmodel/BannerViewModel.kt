package com.example.composedemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.composedemo.data.Repository
import com.example.composedemo.utils.Resource
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray

class BannerViewModel() : ViewModel() {

    fun getNewContent(contentKeys: List<String>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val list = ArrayList<String?>()
            for (item in contentKeys) {
                list.add(item)
            }
            val jsArray = JSONArray(list)
            emit(Resource.success(data = Repository().getBanners(jsArray)))
        } catch (e: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = e.message ?: "Error Occurred!",
                    e
                )
            )
        }
    }

}