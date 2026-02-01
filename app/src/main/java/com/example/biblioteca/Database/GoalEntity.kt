package com.example.biblioteca.Database


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "metas")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val isCompleted: Boolean = false
)