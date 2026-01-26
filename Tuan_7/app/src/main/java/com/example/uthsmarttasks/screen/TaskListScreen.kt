package com.example.uthsmarttasks.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
// DÒNG IMPORT QUAN TRỌNG ĐÃ ĐƯỢC THÊM VÀO
import com.example.uthsmarttasks.model.Task
import com.example.uthsmarttasks.viewmodel.TaskListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onTaskClick: (String) -> Unit,
    viewModel: TaskListViewModel = viewModel()
) {
    // Dùng 'by' để tự động theo dõi và nhận giá trị từ State
    val tasks by viewModel.tasks
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    // LaunchedEffect này sẽ gọi fetchTasks() một lần khi màn hình được tạo
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchTasks()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("UTH SmartTasks") }) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (errorMessage != null) {
                // Chỉ hiển thị Text khi errorMessage có giá trị (không phải null)
                // Dùng !! để khẳng định với trình biên dịch rằng nó không null trong khối lệnh này
                Text(text = errorMessage!!)
            } else if (tasks.isEmpty()) {
                // tasks bây giờ sẽ là danh sách rỗng, không phải null, nên không crash
                EmptyView()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(tasks) { task ->
                        TaskItem(task = task, onClick = { onTaskClick(task.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = task.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text(text = task.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(8.dp))
            Row {
                Text(text = "Status: ${task.status}", style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.weight(1f))
                Text(text = task.deadline, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun EmptyView() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("No Tasks Yet!", style = MaterialTheme.typography.headlineSmall)
        Text("Stay productive - add something to do.")
    }
}
