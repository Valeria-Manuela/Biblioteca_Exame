package com.example.biblioteca.Repository


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.biblioteca.Model.ReadingGoal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GoalsViewModel : ViewModel() {
    private val _goals = mutableStateListOf<ReadingGoal>()
    val goals: List<ReadingGoal> get() = _goals

    fun addGoal(title: String) {
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val newGoal = ReadingGoal(title = title, createdAt = currentDate)
        _goals.add(0, newGoal)
    }

    fun updateGoalStatus(goal: ReadingGoal) {
        val index = _goals.indexOfFirst { it.id == goal.id }
        if (index != -1) {
            _goals[index] = _goals[index].copy(isCompleted = !goal.isCompleted)
        }
    }

    fun updateGoalTitle(id: String, newTitle: String) {
        val index = _goals.indexOfFirst { it.id == id }
        if (index != -1) {
            _goals[index] = _goals[index].copy(title = newTitle)
        }
    }

    fun deleteGoal(goal: ReadingGoal) {
        _goals.remove(goal)
    }
}