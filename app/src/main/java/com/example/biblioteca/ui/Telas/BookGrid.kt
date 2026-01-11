package com.example.biblioteca.ui.Telas


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.biblioteca.Model.BookModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookGrid(
    books: List<BookModel>,
    onBookClick: (BookModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(books) { book ->
            BookCard(
                book = book,
                modifier = Modifier.clickable { onBookClick(book) }
            )
        }
    }
}





