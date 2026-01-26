package com.example.getapi.model

data class TaskDetailResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: Task
)
