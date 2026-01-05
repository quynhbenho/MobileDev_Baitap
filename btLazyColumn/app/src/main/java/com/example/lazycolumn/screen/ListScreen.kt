package com.example.lazycolumn.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column // Dùng Column thường
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListScreen() {
    // Tạo danh sách 1 triệu phần tử
    val items = List(1_000_000) { index ->
        "Item #$index - This will crash your app!"
    }

    // Sử dụng Column thường + verticalScroll
    // Cảnh báo: Nó sẽ cố gắng render toàn bộ 1 triệu item ngay lập tức
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Cho phép cuộn
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = item)
                }
            }
        }
    }
}
