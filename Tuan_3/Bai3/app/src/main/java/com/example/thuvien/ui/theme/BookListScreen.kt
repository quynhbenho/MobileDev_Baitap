package com.example.thuvien.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // <--- Dòng này quan trọng để dùng được items(books)
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thuvien.model.Book


@Composable
fun BookListScreen(
    books: List<Book>,
    onBookChecked: (String, Boolean) -> Unit // Hàm callback khi tick chọn
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)) {
        Text(
            text = "Danh sách sách",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Khung xám bao quanh danh sách
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // Chiều cao cố định cho list
                .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(books) { book ->
                    BookItem(book = book, onChecked = { isChecked ->
                        onBookChecked(book.id, isChecked)
                    })
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onChecked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
            .clickable { onChecked(!book.isSelected) }, // Bấm vào cả dòng cũng ăn
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = book.isSelected,
            onCheckedChange = { onChecked(it) },
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFC2185B))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = book.name, fontSize = 16.sp)
    }
}
