package com.example.getapi.model

data class Task(
    val id: Int,
    val title: String,
    val description: String?,
    val status: String?
)
