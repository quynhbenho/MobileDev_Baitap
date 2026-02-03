package com.example.buoicuoi.repository

import androidx.lifecycle.LiveData
import com.example.buoicuoi.local.TaskDao
import com.example.buoicuoi.local.TaskEntity

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun insert(task: TaskEntity) {
        taskDao.insertTask(task)
    }
}
