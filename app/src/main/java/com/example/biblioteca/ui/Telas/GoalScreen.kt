package com.example.biblioteca.ui.Telas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.biblioteca.Database.GoalDao
import com.example.biblioteca.Database.GoalEntity
import com.example.biblioteca.ViewModel.GoalsViewModel
import com.example.biblioteca.ViewModel.GoalsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadingGoalsScreen(
    goalDao: GoalDao,
    onBackClick: () -> Unit
) {
    val viewModel: GoalsViewModel = viewModel(factory = GoalsViewModelFactory(goalDao))

    val goals by viewModel.goals.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var goalTitleText by remember { mutableStateOf("") }
    var editingGoal: GoalEntity? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Metas de Leitura") },
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
                    editingGoal = null
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
            if (goals.isEmpty()) {
                Text(
                    "Nenhuma meta definida no banco de dados.",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(goals, key = { it.id }) { goal ->
                        GoalCard(
                            goal = goal,
                            onToggle = { viewModel.toggleGoalCompletion(goal) },
                            onDelete = { viewModel.deleteGoal(goal) },
                            onEditClick = {
                                goalTitleText = goal.description
                                editingGoal = goal
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
            title = { Text(if (editingGoal == null) "Nova Meta" else "Editar Meta") },
            text = {
                OutlinedTextField(
                    value = goalTitleText,
                    onValueChange = { goalTitleText = it },
                    label = { Text("O que você pretende ler?") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (goalTitleText.isNotBlank()) {
                        if (editingGoal == null) {
                            viewModel.addGoal(goalTitleText)
                        } else {
                            viewModel.updateGoal(editingGoal!!.copy(description = goalTitleText))
                        }
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

@Composable
fun GoalCard(
    goal: GoalEntity,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = goal.isCompleted, onCheckedChange = { onToggle() })

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = goal.description,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if (goal.isCompleted) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
                )
            }

            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray)
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = Color.Red)
            }
        }
    }
}