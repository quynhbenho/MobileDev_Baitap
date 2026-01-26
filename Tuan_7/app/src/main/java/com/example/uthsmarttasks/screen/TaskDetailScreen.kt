package com.example.uthsmarttasks.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uthsmarttasks.model.TaskDetail
import com.example.uthsmarttasks.remote.ApiService
import com.example.uthsmarttasks.viewmodel.TaskDetailViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: String,
    onNavigateBack: () -> Unit,
    viewModel: TaskDetailViewModel = viewModel()
) {
    val taskDetail by viewModel.taskDetail
    val isLoading by viewModel.isLoading

    LaunchedEffect(key1 = Unit) {
        viewModel.navigateBack.collect { shouldNavigate ->
            if (shouldNavigate) {
                onNavigateBack()
            }
        }
    }

    LaunchedEffect(key1 = taskId) {
        viewModel.fetchTaskDetail(taskId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.deleteTask(taskId) }) {
                        Icon(Icons.Default.Delete, "Delete Task")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            // Hiển thị loading nếu đang tải VÀ chi tiết task chưa có
            if (isLoading && taskDetail == null) {
                CircularProgressIndicator()
            } else if (taskDetail != null) {
                // Chỉ truy cập khi taskDetail chắc chắn không null
                val detail = taskDetail!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(detail.title, style = MaterialTheme.typography.headlineMedium)
                    Text(detail.description, style = MaterialTheme.typography.bodyLarge)
                    Text("Category: ${detail.category}")
                    Text("Status: ${detail.status}")
                    Text("Priority: ${detail.priority}")
                }
            } else {
                // Trường hợp không loading nhưng taskDetail vẫn null (lỗi API)
                Text("Could not load task details.")
            }
        }
    }
}

class TaskDetailViewModel : ViewModel() {
    private val _taskDetail = mutableStateOf<TaskDetail?>(null)
    val taskDetail: State<TaskDetail?> = _taskDetail

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _navigateBack = MutableSharedFlow<Boolean>()
    val navigateBack = _navigateBack.asSharedFlow()

    fun fetchTaskDetail(taskId: String) {
        // CẬP NHẬT BIẾN PRIVATE
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _taskDetail.value = ApiService.instance.getTaskDetail(taskId)
            } catch (e: Exception) {
                Log.e("TaskDetailViewModel", "Error fetching detail for $taskId", e)
                // Nếu lỗi, cũng nên xóa giá trị cũ đi
                _taskDetail.value = null
            } finally {
                // CẬP NHẬT BIẾN PRIVATE
                _isLoading.value = false
            }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            try {
                val response = ApiService.instance.deleteTask(taskId)
                if (response.isSuccessful) {
                    _navigateBack.emit(true)
                }
            } catch (e: Exception) {
                Log.e("TaskDetailViewModel", "Error deleting task $taskId", e)
            }
        }
    }
}