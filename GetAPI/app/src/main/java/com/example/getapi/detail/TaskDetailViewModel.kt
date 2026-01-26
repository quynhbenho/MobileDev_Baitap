package com.example.getapi.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getapi.model.Task
import com.example.getapi.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel : ViewModel() {

    private val repository = TaskRepository()

    private val _task = MutableStateFlow<Task?>(null)
    val task: StateFlow<Task?> = _task

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    fun loadTask(id: Int) {
        viewModelScope.launch {
            try {
                _task.value = repository.getTaskDetail(id)
            } catch (e: Exception) {
                _error.value = true
            }
        }
    }

    fun deleteTask(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteTask(id)
                onSuccess()
            } catch (e: Exception) {
            }
        }
    }
}
