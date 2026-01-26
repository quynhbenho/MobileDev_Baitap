package com.example.uthsmarttasks.remote

import com.example.uthsmarttasks.model.TaskDetail
import com.example.uthsmarttasks.model.TaskListResponse
// ---------------------------------------------------------------------------------

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): TaskListResponse

    @GET("researchUTH/task/{id}")
    suspend fun getTaskDetail(@Path("id") taskId: String): TaskDetail

    @DELETE("researchUTH/task/{id}")
    suspend fun deleteTask(@Path("id") taskId: String): Response<Unit>

    companion object {
        val instance: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://amock.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
