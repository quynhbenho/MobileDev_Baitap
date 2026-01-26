package com.example.dataflow.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.dataflow.components.HeaderLogo
import com.example.dataflow.components.CustomTextField
import com.example.dataflow.components.MainButton
import com.example.dataflow.navigation.Routes

@Composable
fun ForgetPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) } // Biến lưu thông báo lỗi

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        HeaderLogo()

        Spacer(modifier = Modifier.height(40.dp))
        Text("Forget Password?", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Enter your Email, we will send you a verification code.",
            textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Ô nhập Email
        CustomTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null // Xóa lỗi khi người dùng bắt đầu nhập lại
            },
            placeholder = "Your Email",
            icon = Icons.Default.Email
        )

        // Hiển thị lỗi nếu có (dòng chữ đỏ nhỏ bên dưới)
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start).padding(top = 4.dp, start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        MainButton(text = "Next") {
            // Logic kiểm tra lỗi
            if (email.isBlank()) {
                errorMessage = "Bạn chưa nhâp Email"
            } else if (!email.contains("@")) {
                errorMessage = "Email không đúng định dạng"
            } else {
                // Hợp lệ thì chuyển màn hình
                navController.navigate(Routes.getVerifyRoute(email))
            }
        }
    }
}
