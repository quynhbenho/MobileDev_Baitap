package com.example.week3payment.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week3payment.model.ApplePay
import com.example.week3payment.model.GooglePay
import com.example.week3payment.model.PayPal
import com.example.week3payment.model.PaymentMethod

@Composable
fun PaymentScreen() {

    val paymentMethods = listOf<PaymentMethod>(
        PayPal,
        GooglePay,
        ApplePay
    )

    var selected by remember { mutableStateOf<PaymentMethod?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        paymentMethods.forEach { item ->
            PaymentItem(
                method = item,
                isSelected = selected == item,
                onClick = { selected = item }
            )
        }
    }
}
