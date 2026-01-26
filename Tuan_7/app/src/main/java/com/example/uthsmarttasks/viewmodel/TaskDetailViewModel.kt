package com.example.uthsmarttasks.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmarttasks.model.TaskDetail
import com.example.uthsmarttasks.remote.ApiService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

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
