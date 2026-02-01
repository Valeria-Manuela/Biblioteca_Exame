package com.example.biblioteca.Database


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.biblioteca.Model.Book

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val coverUrl: String,
    val description: String
)

fun BookEntity.toModel() = Book(
    id = this.id,
    title = this.title,
    author = this.author,
    coverUrl = this.coverUrl,
    description = this.description
)