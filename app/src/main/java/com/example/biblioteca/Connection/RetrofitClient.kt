package com.example.biblioteca.Network

import com.example.biblioteca.Database.BookEntity
import com.example.biblioteca.Model.ReadingGoal
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("api/goals")
    suspend fun getGoals(): List<ReadingGoal>

    @POST("api/goals")
    suspend fun createGoal(@Body goal: ReadingGoal): ReadingGoal

    @PUT("api/goals/{id}")
    suspend fun updateGoal(@Path("id") id: String, @Body goal: ReadingGoal): ReadingGoal

    @DELETE("api/goals/{id}")
    suspend fun deleteGoal(@Path("id") id: String)

    @GET("api/books")
    suspend fun getBooks(): List<BookEntity>
}

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.3:8080/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}