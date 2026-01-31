package com.example.biblioteca.Database

import androidx.room.*

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val coverUrl: String,
    val description: String
)

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("DELETE FROM books")
    suspend fun clearAll()
}

@Database(entities = [BookEntity::class], version = 1, exportSchema = false) // Adicione aqui
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}

fun BookEntity.toModel() = com.example.biblioteca.Model.Book(
    title = this.title,
    author = this.author,
    coverUrl = this.coverUrl,
    description = this.description
)