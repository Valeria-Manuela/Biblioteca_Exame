package com.example.biblioteca.ui.Telas


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biblioteca.Repository.GoalsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingGoalsScreen(
    onBackClick: () -> Unit,
    viewModel: GoalsViewModel = viewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    var goalTitleText by remember { mutableStateOf("") }
    var editingGoalId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Metas") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingGoalId = null
                    goalTitleText = ""
                    showDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nova Meta", tint = Color.White)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
            if (viewModel.goals.isEmpty()) {
                Text("Nenhuma meta definida.", modifier = Modifier.align(Alignment.Center), color = Color.Gray)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(viewModel.goals, key = { it.id }) { goal ->
                        GoalCard(
                            goal = goal,
                            onToggle = { viewModel.updateGoalStatus(goal) },
                            onDelete = { viewModel.deleteGoal(goal) },
                            onEditClick = {
                                goalTitleText = goal.title
                                editingGoalId = goal.id
                                showDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (editingGoalId == null) "Nova Meta" else "Editar Meta") },
            text = {
                OutlinedTextField(
                    value = goalTitleText,
                    onValueChange = { goalTitleText = it },
                    label = { Text("Título da meta") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (goalTitleText.isNotBlank()) {
                        editingGoalId?.let {
                            viewModel.updateGoalTitle(it, goalTitleText)
                        } ?: viewModel.addGoal(goalTitleText)
                        showDialog = false
                    }
                }) { Text("Salvar") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
            }
        )
    }
}