package com.example.uthsmarttasks.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmarttasks.model.Task
import com.example.uthsmarttasks.remote.ApiService
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    // Biến private, có thể thay đổi giá trị
    private val _tasks = mutableStateOf<List<Task>>(emptyList())
    // Biến public, chỉ cho phép đọc, để UI sử dụng
    val tasks: State<List<Task>> = _tasks

    // Biến private, có thể thay đổi giá trị
    private val _isLoading = mutableStateOf(true)
    // Biến public, chỉ cho phép đọc
    val isLoading: State<Boolean> = _isLoading

    // Biến private, có thể thay đổi giá trị
    private val _errorMessage = mutableStateOf<String?>(null)
    // Biến public, chỉ cho phép đọc
    val errorMessage: State<String?> = _errorMessage

    fun fetchTasks() {
        // CẬP NHẬT BIẾN PRIVATE, KHÔNG PHẢI PUBLIC
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val response = ApiService.instance.getTasks()
                _tasks.value = response.tasks
            } catch (e: Exception) {
                Log.e("TaskListViewModel", "Error fetching tasks", e)
                _errorMessage.value = "Failed to load tasks."
            } finally {
                // CẬP NHẬT BIẾN PRIVATE
                _isLoading.value = false
            }
        }
    }
}
