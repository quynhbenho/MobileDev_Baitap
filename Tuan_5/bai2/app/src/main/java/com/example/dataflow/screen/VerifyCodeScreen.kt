package com.example.dataflow.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dataflow.components.BackButton
import com.example.dataflow.components.HeaderLogo
import com.example.dataflow.components.MainButton
import com.example.dataflow.navigation.Routes

@Composable
fun VerifyCodeScreen(navController: NavController, email: String) {
    val code = List(5) { remember { mutableStateOf("") } }
    val focusRequesters = List(5) { FocusRequester() }

    // DÙNG BOX ĐỂ BỌC TOÀN BỘ MÀN HÌNH
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Đặt nút Back ở góc trên cùng bên trái của Box
        BackButton { navController.popBackStack() }

        // Dùng Column để chứa phần còn lại của nội dung, căn giữa
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Spacer để đẩy nội dung xuống dưới nút Back
            Spacer(modifier = Modifier.height(60.dp))

            HeaderLogo()
            Spacer(modifier = Modifier.height(30.dp))
            Text("Verify Code", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Enter the code we just sent you on your registered Email",
                textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                code.forEachIndexed { index, digitState ->
                    OutlinedTextField(
                        value = digitState.value,
                        onValueChange = { newValue ->
                            if (newValue.length <= 1) {
                                digitState.value = newValue
                                if (newValue.isNotEmpty()) {
                                    if (index < 4) focusRequesters[index + 1].requestFocus()
                                } else {
                                    if (index > 0) focusRequesters[index - 1].requestFocus()
                                }
                            } else {
                                if (index < 4) focusRequesters[index + 1].requestFocus()
                                digitState.value = newValue.last().toString()
                            }
                        },
                        modifier = Modifier
                            .width(50.dp)
                            .height(60.dp)
                            .background(Color(0xA1F5F6FA), RoundedCornerShape(8.dp))
                            .focusRequester(focusRequesters[index]),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF5F6FA),
                            unfocusedContainerColor = Color(0xA9F5F6FA),
                            focusedBorderColor = Color(0xFF007BFF),
                            unfocusedBorderColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            MainButton(text = "Next") {
                val fullCode = code.joinToString("") { it.value }
                if (fullCode.length == 5) {
                    navController.navigate(Routes.getResetRoute(email, fullCode))
                }
            }
        }
    }
}
