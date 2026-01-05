package com.example.dataflow.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dataflow.R // <-- Đảm bảo R import đúng package của bro

// 1. Logo
@Composable
fun HeaderLogo() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.anh1), // Thay ảnh logo của bro
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = "SmartTasks",
            color = Color(0xFF007BFF),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

// 2. Nút Back (QUAN TRỌNG: Xử lý sự kiện click ở đây)
@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick, // Khi bấm vào icon, nó sẽ gọi hàm onClick (popBackStack) truyền từ bên ngoài vào
        modifier = Modifier
            .background(Color(0xFFF5F6FA), shape = RoundedCornerShape(50)) // Nền xám tròn nhẹ
            .size(40.dp) // Kích thước nút
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color(0xFF007BFF), // Màu mũi tên xanh
            modifier = Modifier.size(20.dp)
        )
    }
}

// 3. TextField (Ô nhập liệu)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector? = null,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        leadingIcon = if (icon != null) {
            { Icon(imageVector = icon, contentDescription = null, tint = Color.Gray) }
        } else null,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F6FA), shape = RoundedCornerShape(8.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF007BFF),
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color(0xFFF5F6FA),
            unfocusedContainerColor = Color(0xFFF5F6FA)
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true
    )
}

// 4. Button Chính
@Composable
fun MainButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
        shape = RoundedCornerShape(25.dp)
    ) {
        Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
