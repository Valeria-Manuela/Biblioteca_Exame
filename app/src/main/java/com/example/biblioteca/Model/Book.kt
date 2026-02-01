package com.example.biblioteca.Model


data class Book(
    val id:Int,
    val title: String,
    val author: String,
    val coverUrl: String,
    val description: String,
    val isSaved: Boolean = false
)