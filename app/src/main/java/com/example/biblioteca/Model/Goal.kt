package com.example.biblioteca.Model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ReadingGoal(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("description")
    val description: String = "",

    @SerializedName("completed")
    val completed: Boolean = false,

    @SerializedName("createdAt")
    val createdAt: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
)