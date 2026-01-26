package com.example.btapi.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btapi.model.Product
import com.example.btapi.remote.ApiService
import kotlinx.coroutines.launch
import kotlin.random.Random // <-- THÊM IMPORT NÀY

class ProductViewModel : ViewModel() {
    var product: Product? by mutableStateOf(null)
        private set

    init {
        fetchProduct()
    }

    private fun fetchProduct() {
        viewModelScope.launch {
            try {
                // ========== SỬA Ở ĐÂY ==========
                // Tạo một số ngẫu nhiên từ 1 đến 100 (vì DummyJSON có 100 sản phẩm)
                val randomId = Random.nextInt(1, 101)
                // Gọi API với ID ngẫu nhiên
                val fetchedProduct = ApiService.getInstance().getProduct(randomId)
                // =============================

                Log.d("ProductViewModel", "Sản phẩm nhận được (ID: $randomId): $fetchedProduct")

                product = fetchedProduct
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Lỗi khi gọi API: ${e.message}", e)
            }
        }
    }
}
