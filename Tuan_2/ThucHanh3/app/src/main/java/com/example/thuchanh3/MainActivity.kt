package com.example.thuchanh3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThucHanh3Screen()
        }
    }
}

@Composable
fun ThucHanh3Screen() {

    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var selectedOp by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Thực hành 03",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Input số 1
        TextField(
            value = num1,
            onValueChange = { num1 = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Các phép toán
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            OperatorButton(
                text = "+",
                color = Color(0xFFE53935),
                isSelected = selectedOp == "+"
            ) { selectedOp = "+" }

            OperatorButton(
                text = "-",
                color = Color(0xFFFBC02D),
                isSelected = selectedOp == "-"
            ) { selectedOp = "-" }

            OperatorButton(
                text = "*",
                color = Color(0xFF5E35B1),
                isSelected = selectedOp == "*"
            ) { selectedOp = "*" }

            OperatorButton(
                text = "/",
                color = Color.Green,
                isSelected = selectedOp == "/"
            ) { selectedOp = "/" }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Input số 2
        TextField(
            value = num2,
            onValueChange = { num2 = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Tính kết quả
        LaunchedEffect(num1, num2, selectedOp) {
            val a = num1.toDoubleOrNull()
            val b = num2.toDoubleOrNull()

            result = if (a != null && b != null && selectedOp.isNotEmpty()) {
                when (selectedOp) {
                    "+" -> (a + b).toInt().toString()
                    "-" -> (a - b).toInt().toString()
                    "*" -> (a * b).toInt().toString()
                    "/" -> if (b != 0.0) {
                        (a / b).toString()
                    } else {
                        "Không chia cho 0"
                    }
                    else -> ""
                }
            } else {
                ""
            }
        }

        Text(
            text = if (result.isNotEmpty()) "Kết quả: $result" else "Kết quả:",
            fontSize = 16.sp
        )
    }
}

@Composable
fun OperatorButton(
    text: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(55.dp)
            .background(color, RoundedCornerShape(10.dp))
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(10.dp)
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(text, color = Color.White, fontSize = 18.sp)
        }
    }
}
