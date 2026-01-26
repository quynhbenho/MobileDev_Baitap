package com.example.getapi.model

data class TaskListResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: List<Task>
)
