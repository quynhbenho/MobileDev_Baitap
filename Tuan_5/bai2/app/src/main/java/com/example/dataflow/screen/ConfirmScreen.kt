package com.example.dataflow.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.dataflow.navigation.Routes

@Composable
fun ConfirmScreen(navController: NavController, email: String, code: String, password: String) {
    // Lấy context để hiển thị Toast (Thông báo)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- PHẦN 1: Nút Back ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            BackButton {
                navController.popBackStack() // Nút này vẫn quay lại bước trước để sửa nếu cần
            }
        }

        // --- PHẦN 2: Nội dung ---
        HeaderLogo()

        Spacer(modifier = Modifier.height(30.dp))
        Text("Confirm", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "We are here to help you!",
            textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Hiển thị thông tin (Không cho sửa)
        CustomTextField(value = email, onValueChange = {}, placeholder = "", icon = Icons.Default.Person)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(value = code, onValueChange = {}, placeholder = "", icon = Icons.Default.Email)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(value = password, onValueChange = {}, placeholder = "", icon = Icons.Default.Lock)

        Spacer(modifier = Modifier.height(30.dp))

        // --- NÚT SUMMIT: LƯU VÀ QUAY VỀ ĐẦU ---
        MainButton(text = "Summit") {
            // 1. Hiển thị thông báo "Đã lưu"
            Toast.makeText(context, "Lưu dữ liệu thành công!", Toast.LENGTH_SHORT).show()

            // 2. Quay về màn hình đầu tiên (ForgetPasswordScreen)
            navController.navigate(Routes.FORGET) {
                // Xóa sạch lịch sử các màn hình cũ để không bị nặng máy
                popUpTo(0) { inclusive = true }
            }
        }
    }
}
