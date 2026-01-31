package com.example.biblioteca.Repository

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biblioteca.Model.ReadingGoal
import com.example.biblioteca.Network.RetrofitClient
import kotlinx.coroutines.launch

class GoalsViewModel : ViewModel() {
    private val _goals = mutableStateListOf<ReadingGoal>()
    val goals: List<ReadingGoal> get() = _goals

    init {
        loadGoals()
    }

    private fun loadGoals() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getGoals()
                _goals.clear()
                _goals.addAll(response)
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun addGoal(title: String) {
        viewModelScope.launch {
            try {
                // Criamos o objeto (o Model já gera ID e Data por padrão agora)
                val newGoal = ReadingGoal(title = title)
                val savedGoal = RetrofitClient.instance.createGoal(newGoal)
                _goals.add(0, savedGoal)
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    // NOVA FUNÇÃO: Atualiza o TÍTULO da meta
    fun updateGoalTitle(id: String, newTitle: String) {
        viewModelScope.launch {
            try {
                val goalOriginal = _goals.find { it.id == id }
                goalOriginal?.let {
                    val updatedGoal = it.copy(title = newTitle)
                    val response = RetrofitClient.instance.createGoal(updatedGoal)

                    val index = _goals.indexOfFirst { it.id == id }
                    if (index != -1) _goals[index] = response
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun updateGoalStatus(goal: ReadingGoal) {
        viewModelScope.launch {
            try {
                val updated = goal.copy(isCompleted = !goal.isCompleted)
                val response = RetrofitClient.instance.createGoal(updated)

                val index = _goals.indexOfFirst { it.id == goal.id }
                if (index != -1) _goals[index] = response
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun deleteGoal(goal: ReadingGoal) {
        viewModelScope.launch {
            try {
                RetrofitClient.instance.deleteGoal(goal.id)
                _goals.remove(goal)
            } catch (e: Exception) { e.printStackTrace() }
        }
    }
}