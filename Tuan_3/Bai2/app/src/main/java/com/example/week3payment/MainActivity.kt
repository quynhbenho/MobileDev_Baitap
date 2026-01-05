package com.example.week3payment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// import com.example.week3payment.ui.theme.Bai2Theme // Bỏ comment nếu muốn dùng theme

// -----------------------------------------------------------
// 1. DATA CLASS: Sửa icon thành Int để chứa R.drawable...
// -----------------------------------------------------------
data class PaymentMethod(
    val id: Int,
    val name: String,
    val iconResId: Int, // <-- Quan trọng: Kiểu Int để lưu ID ảnh
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    PaymentScreen()
                }
            }
        }
    }
}

@Composable
fun PaymentScreen() {
    // -----------------------------------------------------------
    // KHAI BÁO DỮ LIỆU: Thay R.drawable.ten_anh_cua_ban vào đây
    // -----------------------------------------------------------
    val paymentMethods = listOf(
        // Bạn phải chép ảnh paypal.png, google.png, apple.png vào res/drawable trước nhé
        PaymentMethod(1, "PayPal", R.drawable.ic_paypal),
        PaymentMethod(2, "Google Pay", R.drawable.ic_googlepay),
        PaymentMethod(3, "Apple Pay", R.drawable.ic_applepay)
    )

    var selectedMethod by remember { mutableStateOf<PaymentMethod?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // --- PHẦN HIỂN THỊ LOGO TO ---
        if (selectedMethod == null) {
            // Ảnh mặc định khi chưa chọn (ví dụ hình cái ví)
            // Nhớ chép file wallet.png vào drawable, hoặc đổi tạm thành R.drawable.paypal để test
            Image(
                painter = painterResource(id = R.drawable.ic_wallet),
                contentDescription = "Default",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )
            Text("Chọn hình thức thanh toán", color = Color.Gray, modifier = Modifier.padding(top = 10.dp))
        } else {
            // Ảnh logo to khi đã chọn
            Image(
                painter = painterResource(id = selectedMethod!!.iconResId), // <-- Lệnh lấy ảnh đây
                contentDescription = selectedMethod!!.name,
                modifier = Modifier.size(150.dp), // Logo to hơn chút
                contentScale = ContentScale.Fit
            )
            // (Đã xóa phần tô màu text để giao diện sạch sẽ giống ảnh mẫu hơn)
        }

        Spacer(modifier = Modifier.height(60.dp))

        // --- DANH SÁCH LỰA CHỌN ---
        paymentMethods.forEach { method ->
            PaymentOptionItem(
                method = method,
                isSelected = (selectedMethod == method),
                onSelect = { selectedMethod = method }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- NÚT CONTINUE ---
        if (selectedMethod != null) {
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4))
            ) {
                Text(text = "Continue", fontSize = 18.sp, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun PaymentOptionItem(
    method: PaymentMethod,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) Color(0xFF4285F4) else Color.LightGray, // Viền xanh khi chọn
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .clickable { onSelect() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Radio Button
        RadioButton(
            selected = isSelected,
            onClick = { onSelect() },
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF4285F4))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = method.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        // --- ẢNH LOGO NHỎ BÊN PHẢI ---
        Image(
            painter = painterResource(id = method.iconResId), // <-- Lệnh lấy ảnh
            contentDescription = null,
            modifier = Modifier.width(50.dp).height(30.dp), // Kích thước logo nhỏ
            contentScale = ContentScale.Fit
        )
    }
}
