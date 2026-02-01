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
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "biblioteca-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibliotecaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(
                        bookDao = db.bookDao(),
                        goalDao = db.goalDao(),
                        userDao = db.userDao()
                    )
                }
            }
        }
    }
}