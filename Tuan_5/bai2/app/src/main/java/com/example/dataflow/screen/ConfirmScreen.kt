package com.example.dataflow.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import com.example.dataflow.components.BackButton
import com.example.dataflow.components.HeaderLogo
import com.example.dataflow.components.CustomTextField
import com.example.dataflow.components.MainButton

@Composable
fun ConfirmScreen(navController: NavController, email: String, code: String, password: String) {
    // DÙNG BOX BỌC BÊN NGOÀI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Đặt nút Back ở góc trên cùng bên trái
        BackButton { navController.popBackStack() }

        // Column chứa phần còn lại
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Spacer để đẩy nội dung xuống dưới nút Back
            Spacer(modifier = Modifier.height(60.dp))

            HeaderLogo()
            Spacer(modifier = Modifier.height(30.dp))
            Text("Confirm", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "We are here to help you!",
                textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(30.dp))

            CustomTextField(value = email, onValueChange = {}, placeholder = "", icon = Icons.Default.Person)
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(value = code, onValueChange = {}, placeholder = "", icon = Icons.Default.Email)
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(value = password, onValueChange = {}, placeholder = "", icon = Icons.Default.Lock)

            Spacer(modifier = Modifier.height(30.dp))
            MainButton(text = "Summit") {
                // Xử lý hoàn tất
            }
        }
    }
}
