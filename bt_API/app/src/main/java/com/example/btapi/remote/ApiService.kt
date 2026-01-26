package com.example.btapi.remote

import com.example.btapi.model.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path // <-- THÊM IMPORT NÀY

interface ApiService {
    // ========== SỬA Ở ĐÂY ==========
    // Sử dụng {id} để có thể truyền ID sản phẩm động
    @GET("products/{id}")
    suspend fun getProduct(@Path("id") productId: Int): Product
    // =============================

    companion object {
        private var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://dummyjson.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}
