package com.example.biblioteca.Model

import java.util.UUID
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ReadingGoal(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val createdAt: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()),
    var isCompleted: Boolean = false
)