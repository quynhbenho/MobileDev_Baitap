package com.example.getapi.repository

import com.example.getapi.model.Task
import com.example.getapi.network.RetrofitClient

class TaskRepository {

    private val api = RetrofitClient.apiService

    suspend fun getTasks(): List<Task> {
        return api.getTasks().data
    }

    suspend fun getTaskDetail(id: Int): Task {
        return api.getTaskDetail(id).data
    }

    suspend fun deleteTask(id: Int) {
        api.deleteTask(id)
    }
}
