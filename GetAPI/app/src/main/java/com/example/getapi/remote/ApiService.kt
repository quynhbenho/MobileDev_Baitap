package com.example.getapi.remote

import com.example.getapi.model.Task
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("tasks")
    suspend fun getTasks(): List<Task>

    @GET("task/{id}")
    suspend fun getTaskDetail(@Path("id") id: Int): Task

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") id: Int)
}
