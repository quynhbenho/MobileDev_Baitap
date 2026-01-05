package com.example.lazycolumn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lazycolumn.screen.HomeScreen
import com.example.lazycolumn.screen.LazyListScreen
import com.example.lazycolumn.screen.ListScreen // Import màn hình mới

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("lazy") {
            LazyListScreen()
        }
        // Thêm route mới ở đây
        composable("normal_list") {
            ListScreen()
        }
    }
}

