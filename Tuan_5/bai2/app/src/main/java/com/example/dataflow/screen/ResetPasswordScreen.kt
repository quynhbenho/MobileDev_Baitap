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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.dataflow.components.BackButton
import com.example.dataflow.components.HeaderLogo
import com.example.dataflow.components.CustomTextField
import com.example.dataflow.components.MainButton
import com.example.dataflow.navigation.Routes

@Composable
fun ResetPasswordScreen(navController: NavController, email: String, code: String) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

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
            Text("Create new password", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Your new password must be different form previously used password",
                textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(30.dp))

            CustomTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                },
                placeholder = "Password",
                icon = Icons.Default.Lock,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = null
                },
                placeholder = "Confirm Password",
                icon = Icons.Default.Lock,
                isPassword = true
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp, start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            MainButton(text = "Next") {
                if (password.isEmpty() || confirmPassword.isEmpty()) {
                    errorMessage = "Vui lòng nhập đầy đủ thông tin"
                } else if (password != confirmPassword) {
                    errorMessage = "Sai pass (Mật khẩu không trùng khớp)"
                } else {
                    navController.navigate(Routes.getConfirmRoute(email, code, password))
                }
            }
        }
    }
}
