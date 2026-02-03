package com.example.buoicuoi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.buoicuoi.add.AddTaskScreen
import com.example.buoicuoi.list.TaskListScreen
import com.example.buoicuoi.ui.theme.BuoiCuoiTheme
import com.example.buoicuoi.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuoiCuoiTheme {
                AppNavigator(taskViewModel)
            }
        }
    }
}

object AppDestinations {
    const val TASK_LIST = "task_list"
    const val ADD_TASK = "add_task"
}

@Composable
fun AppNavigator(taskViewModel: TaskViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppDestinations.TASK_LIST) {
        composable(AppDestinations.TASK_LIST) {
            TaskListScreen(
                viewModel = taskViewModel,
                onAddTaskClicked = {
                    navController.navigate(AppDestinations.ADD_TASK)
                }
            )
        }
        composable(AppDestinations.ADD_TASK) {
            AddTaskScreen(
                viewModel = taskViewModel,

                navController = navController,
                onTaskAdded = {
                    navController.popBackStack()
                }
            )
        }
    }
}
