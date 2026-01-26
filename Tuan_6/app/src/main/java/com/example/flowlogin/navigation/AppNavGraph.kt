package com.example.flowlogin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flowlogin.login.LoginScreen
import com.example.flowlogin.profile.ProfileScreen // <-- Nhớ import ProfileScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        // Route cho màn hình Login
        composable("login") {
            LoginScreen(navController = navController)
        }

        // ▼▼▼ THÊM ĐOẠN CODE NÀY VÀO ▼▼▼
        // Route cho màn hình Profile
        composable("profile") {
            // Chúng ta sẽ tạo một ProfileScreen cơ bản ở bước tiếp theo
            ProfileScreen(navController = navController)
        }
    }
}
