package com.example.uthsmarttasks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uthsmarttasks.screen.TaskDetailScreen
import com.example.uthsmarttasks.screen.TaskListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "taskList") {
        composable("taskList") {
            TaskListScreen(onTaskClick = { taskId ->
                navController.navigate("taskDetail/$taskId")
            })
        }
        composable("taskDetail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            if (taskId != null) {
                TaskDetailScreen(taskId = taskId, onNavigateBack = {
                    navController.popBackStack()
                })
            }
        }
    }
}
