package com.example.getapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.getapi.detail.TaskDetailScreen
import com.example.getapi.list.TaskListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(navController, startDestination = "list") {

        composable("list") {
            TaskListScreen(navController)
        }

        composable("detail/{id}") {
            val id = it.arguments?.getString("id")!!.toInt()
            TaskDetailScreen(navController, id)
        }
    }
}
