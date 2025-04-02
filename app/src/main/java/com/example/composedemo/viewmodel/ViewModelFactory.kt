package com.example.composedemo.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory (val context: Context?=null) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BannerViewModel::class.java)) {
            return BannerViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}