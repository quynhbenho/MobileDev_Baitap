package com.example.dataflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dataflow.screen.*

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.FORGET // <--- QUAN TRỌNG: Chạy thẳng vào màn nhập Email
    ) {
        // --- XÓA CÁC DÒNG CỦA SPLASH VÀ ONBOARDING ĐI ---

        // Chỉ giữ lại các màn hình sau:

        composable(Routes.FORGET) {
            ForgetPasswordScreen(navController)
        }

        composable(Routes.VERIFY) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerifyCodeScreen(navController, email)
        }

        composable(Routes.RESET) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val code = backStackEntry.arguments?.getString("code") ?: ""
            ResetPasswordScreen(navController, email, code)
        }

        composable(Routes.CONFIRM) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val code = backStackEntry.arguments?.getString("code") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            ConfirmScreen(navController, email, code, password)
        }
    }
}
