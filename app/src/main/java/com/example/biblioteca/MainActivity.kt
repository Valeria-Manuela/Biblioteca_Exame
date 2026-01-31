package com.example.biblioteca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.biblioteca.Database.AppDatabase
import com.example.biblioteca.Database.BookEntity
import com.example.biblioteca.Navigation.AppNavigation
import com.example.biblioteca.ui.theme.BibliotecaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "biblioteca-db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val bookDao = db.bookDao()
            val currentBooks = bookDao.getAllBooks()

            if (currentBooks.isEmpty()) {
                val initialBooks = listOf(
                    BookEntity(title = "Clean Code", author = "Robert C. Martin", coverUrl = "https://covers.openlibrary.org/b/id/9610926-L.jpg", description = "Um guia fundamental sobre código limpo."),
                    BookEntity(title = "The Pragmatic Programmer", author = "Andrew Hunt", coverUrl = "https://covers.openlibrary.org/b/id/10521213-L.jpg", description = "Dicas práticas para desenvolvedores."),
                    BookEntity(title = "Design Patterns", author = "Erich Gamma", coverUrl = "https://covers.openlibrary.org/b/id/8231996-L.jpg", description = "Soluções reutilizáveis para software."),
                    BookEntity(title = "Refactoring", author = "Martin Fowler", coverUrl = "https://covers.openlibrary.org/b/id/10435343-L.jpg", description = "Melhorando o design de códigos existentes."),
                    BookEntity(title = "Effective Java", author = "Joshua Bloch", coverUrl = "https://covers.openlibrary.org/b/id/10521234-L.jpg", description = "Melhores práticas para Java.")
                )
                bookDao.insertBooks(initialBooks)
            }
        }

        enableEdgeToEdge()
        setContent {
            BibliotecaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Passando o DAO para a Navegação
                    AppNavigation(db.bookDao())
                }
            }
        }
    }
}