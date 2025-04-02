package com.example.composedemo.utils

import androidx.annotation.Keep
import com.example.composedemo.utils.Status.*

@Keep
data class Resource<out T>(
    val status: Status, val data: T?, val message: String?, val exception: Exception? = null
) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(
            status = SUCCESS, data = data, message = null, exception = null)
        fun <T> error(data: T?, message: String, exception: Exception? = null): Resource<T> =
            Resource(status = ERROR, data = data, message = message, exception = exception)
        fun <T> loading(data: T?): Resource<T> = Resource(
            status = LOADING,
            data = data,
            message = null,
            exception = null
        )
    }
}