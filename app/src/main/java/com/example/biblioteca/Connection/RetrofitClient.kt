package com.example.biblioteca.Network

import com.example.biblioteca.Model.ReadingGoal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface GoalApiService {
    @GET("api/goals")
    suspend fun getGoals(): List<ReadingGoal>

    @POST("api/goals")
    suspend fun createGoal(@Body goal: ReadingGoal): ReadingGoal

    @DELETE("api/goals/{id}")
    suspend fun deleteGoal(@Path("id") id: String)
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.8:8080/"

    val instance: GoalApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoalApiService::class.java)
    }
}