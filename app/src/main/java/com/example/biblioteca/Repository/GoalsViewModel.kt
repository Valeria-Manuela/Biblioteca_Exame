package com.example.biblioteca.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.biblioteca.Database.GoalDao
import com.example.biblioteca.Database.GoalEntity
import com.example.biblioteca.Model.ReadingGoal
import com.example.biblioteca.Network.RetrofitClient
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GoalsViewModel(private val goalDao: GoalDao) : ViewModel() {

    val goals: StateFlow<List<GoalEntity>> = goalDao.getAllGoals()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        refreshGoalsFromApi()
    }

    private fun refreshGoalsFromApi() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getGoals()
                goalDao.insertGoals(response.map { it.toEntity() })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addGoal(title: String) {
        viewModelScope.launch {
            try {
                val newGoal = GoalEntity(description = title)
                goalDao.insertGoal(newGoal)
                RetrofitClient.instance.createGoal(newGoal.toModel())
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun toggleGoalCompletion(goal: GoalEntity) {
        viewModelScope.launch {
            try {
                val updated = goal.copy(isCompleted = !goal.isCompleted)
                goalDao.updateGoal(updated)
                RetrofitClient.instance.updateGoal(updated.id.toString(), updated.toModel())
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun updateGoal(goal: GoalEntity) {
        viewModelScope.launch {
            try {
                goalDao.updateGoal(goal)
                RetrofitClient.instance.updateGoal(goal.id.toString(), goal.toModel())
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun deleteGoal(goal: GoalEntity) {
        viewModelScope.launch {
            try {
                goalDao.deleteGoal(goal)
                RetrofitClient.instance.deleteGoal(goal.id.toString())
            } catch (e: Exception) { e.printStackTrace() }
        }
    }
}

fun ReadingGoal.toEntity() = GoalEntity(
    id = this.id.toIntOrNull() ?: 0,
    description = this.title,
    isCompleted = this.isCompleted
)

fun GoalEntity.toModel() = ReadingGoal(
    id = this.id.toString(),
    title = this.description,
    isCompleted = this.isCompleted
)

class GoalsViewModelFactory(private val goalDao: GoalDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalsViewModel(goalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}