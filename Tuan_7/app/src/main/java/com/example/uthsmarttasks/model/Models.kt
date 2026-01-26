package com.example.uthsmarttasks.model

import com.google.gson.annotations.SerializedName

data class TaskListResponse(val tasks: List<Task>)

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    @SerializedName("created_at", alternate = ["deadline"]) val deadline: String
)

data class TaskDetail(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val status: String,
    val priority: String,
    val subtasks: List<Subtask>,
    val attachments: List<Attachment>
)

data class Subtask(
    val title: String,
    @SerializedName("is_completed", alternate = ["isCompleted"]) val isCompleted: Boolean
)

data class Attachment(
    @SerializedName("name", alternate = ["fileName"]) val fileName: String,
    val url: String
)
