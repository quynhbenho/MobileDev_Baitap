package com.example.flowlogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// import androidx.navigation.compose.rememberNavController // Không cần dòng này nữa
import com.example.flowlogin.navigation.AppNavGraph
import com.example.flowlogin.ui.theme.FlowloginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowloginTheme {
                // ▼▼▼ SỬA LẠI CHỈ CÒN DÒNG NÀY ▼▼▼
                // AppNavGraph sẽ tự tạo và quản lý NavController của riêng nó.
                AppNavGraph()
            }
        }
    }
}
