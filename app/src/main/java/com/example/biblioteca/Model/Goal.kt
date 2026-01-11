package com.example.biblioteca.Model
import java.util.UUID

data class ReadingGoal(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val createdAt: String,
    val isCompleted: Boolean = false
)