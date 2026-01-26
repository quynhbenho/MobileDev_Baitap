package com.example.getapi.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.getapi.components.EmptyView

@Composable
fun TaskListScreen(navController: NavController) {

    val vm: TaskViewModel = viewModel()
    val tasks by vm.tasks.collectAsState()
    val loading by vm.loading.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadTasks()
    }

    when {
        loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        tasks.isEmpty() -> {
            EmptyView()
        }

        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onClick = {
                            navController.navigate("detail/${task.id}")
                        }
                    )
                }
            }
        }
    }
}
