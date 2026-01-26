package com.example.getapi.network

import com.example.getapi.model.TaskDetailResponse
import com.example.getapi.model.TaskListResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // LIST TASKS
    @GET("researchUTH/tasks")
    suspend fun getTasks(): TaskListResponse

    // TASK DETAIL
    @GET("researchUTH/task/{id}")
    suspend fun getTaskDetail(
        @Path("id") id: Int
    ): TaskDetailResponse

    // DELETE TASK
    @DELETE("researchUTH/task/{id}")
    suspend fun deleteTask(
        @Path("id") id: Int
    )
}
