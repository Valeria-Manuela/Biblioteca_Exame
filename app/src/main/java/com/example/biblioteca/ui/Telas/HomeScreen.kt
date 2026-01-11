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
import com.example.biblioteca.Model.BookModel


@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    val mockBooks = listOf(
        BookModel("Clean Code", "Robert C. Martin", "https://covers.openlibrary.org/b/id/9610926-L.jpg", "Um guia fundamental sobre código limpo."),
        BookModel("The Pragmatic Programmer", "Andrew Hunt", "https://covers.openlibrary.org/b/id/10521213-L.jpg", "Dicas práticas para desenvolvedores."),
        BookModel("Design Patterns", "Erich Gamma", "https://covers.openlibrary.org/b/id/8231996-L.jpg", "Soluções reutilizáveis para software."),
        BookModel("Refactoring", "Martin Fowler", "https://covers.openlibrary.org/b/id/10435343-L.jpg", "Melhorando o design de códigos existentes."),
        BookModel("Effective Java", "Joshua Bloch", "https://covers.openlibrary.org/b/id/10521234-L.jpg", "Melhores práticas para Java."),
    )

    val filteredBooks = mockBooks.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.author.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("reading_goals")
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Meta")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Aplicamos o padding aqui
                .background(Color(0xFFF5F5F5))
        ) {
            Text(
                text = "Biblioteca",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Pesquise por título ou autor...") },
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

            if (filteredBooks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhum livro encontrado.", color = Color.Gray)
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