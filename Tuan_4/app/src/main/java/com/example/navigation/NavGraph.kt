package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.screen.*

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("components") {
            ComponentsListScreen(navController)
        }
        // --- CÁC DÒNG DƯỚI ĐÂY ĐÃ ĐƯỢC CẬP NHẬT THÊM navController ---
        composable("text") {
            TextDetailScreen(navController) // Truyền navController vào
        }
        composable("image") {
            ImageScreen(navController)      // Truyền navController vào
        }
        composable("textfield") {
            TextFieldScreen(navController)  // Truyền navController vào
        }
        composable("row") {
            RowLayoutScreen(navController)  // Truyền navController vào
        }
        // --- THÊM CÁC DÒNG NÀY ---
        composable("buttons") {ButtonScreen(navController)
        }
        composable("box") {
            BoxScreen(navController)
        }
        composable("switch_checkbox") {
            SwitchCheckboxScreen(navController)
        }
        composable("slider") {
            SliderScreen(navController)
        }
    }
}
