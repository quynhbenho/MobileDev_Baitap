package com.example.getapi.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getapi.model.Task
import com.example.getapi.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val repository = TaskRepository()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadTasks() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _tasks.value = repository.getTasks()
            } catch (e: Exception) {
                _tasks.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }
}
