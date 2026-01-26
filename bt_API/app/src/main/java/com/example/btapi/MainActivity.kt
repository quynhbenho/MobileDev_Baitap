package com.example.btapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.btapi.model.Product // Đảm bảo import này đúng
import com.example.btapi.ui.theme.BtapiTheme
import com.example.btapi.viewmodel.ProductViewModel
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val productViewModel by viewModels<ProductViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BtapiTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Product detail") },
                            navigationIcon = {
                                IconButton(onClick = { /* TODO: Xử lý khi nhấn nút back */ }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White
                            )
                        )
                    },
                    containerColor = Color(0xFFEEEEEE) // Màu nền xám bên ngoài
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val product = productViewModel.product
                        if (product != null) {
                            ProductDetailScreen(product = product)
                        } else {
                            // Hiển thị loading trong khi chờ dữ liệu từ API
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(product: Product) {
    // Dùng để định dạng tiền tệ
    val format = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            // Tải ảnh từ URL của API DummyJSON
            AsyncImage(
                model = product.image,
                contentDescription = "Hình ảnh sản phẩm ${product.name}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            // Cột chứa thông tin
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Hiển thị tên (là trường "title" từ API)
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )

                // Hiển thị giá (price đã là Double nên định dạng trực tiếp)
                Text(
                    text = "Giá: ${format.format(product.price)}",
                    color = Color.Red,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                // Phần mô tả với nền riêng
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
fun DefaultPreview() {
    BtapiTheme {
        // Dữ liệu giả khớp với data class mới để xem trước
        ProductDetailScreen(
            product = Product(
                id = 1,
                name = "iPhone 9",
                image = "",
                description = "An apple mobile which is nothing like apple...",
                price = 549.0 // price là Double
            )
        )
    }
}
