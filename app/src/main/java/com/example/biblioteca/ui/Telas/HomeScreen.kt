package com.example.biblioteca.ui.Telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.biblioteca.Database.BookDao
import com.example.biblioteca.Database.toModel
import com.example.biblioteca.Model.Book
import com.example.biblioteca.ui.Telas.BookGrid// Import do seu componente de grid

@Composable
fun HomeScreen(navController: NavController, bookDao: BookDao) {
    var searchQuery by remember { mutableStateOf("") }

    val books by produceState<List<com.example.biblioteca.Model.Book>>(initialValue = emptyList()) {
        try {
            val entities = bookDao.getAllBooks()
            value = entities.map { it.toModel() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Filtra a lista baseada no que vem do banco
    val filteredBooks = books.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.author.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Aqui você navegaria para a tela de criação que persistirá no Spring/Room
                    navController.navigate("reading_goals")
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Meta/Livro")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            Text(
                text = "Biblioteca",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
            )

            // Campo de Pesquisa
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Pesquise no banco por título ou autor...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Limpar")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )

            // Lógica de exibição da Lista
            if (filteredBooks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (searchQuery.isEmpty()) "Aguardando livros do banco..." else "Nenhum livro encontrado.",
                        color = Color.Gray
                    )
                }
            } else {
                BookGrid(
                    books = filteredBooks,
                    onBookClick = { book ->
                        val encodedTitle = java.net.URLEncoder.encode(book.title, "UTF-8")
                        val encodedUrl = java.net.URLEncoder.encode(book.coverUrl, "UTF-8")
                        navController.navigate("book_details/$encodedTitle/$encodedUrl")
                    }
                )
            }
        }
    }
}