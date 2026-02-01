package com.example.biblioteca.ViewModel

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.biblioteca.Database.AppDatabase
import com.example.biblioteca.Database.UserEntity
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    var name by mutableStateOf("")
    var phone by mutableStateOf("+2449")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    private val phoneRegex = "^\\+2449[0-9]{8}$".toRegex()
    private val passwordRegex = "^(?=.*[@#$%!.*]).{4,}$".toRegex()

    fun register(onSuccess: () -> Unit) {
        errorMessage = null
        val currentName = name
        val currentPhone = phone
        val currentPassword = password

        when {
            currentName.isBlank() -> errorMessage = "Insira o seu nome completo"
            !currentPhone.matches(phoneRegex) -> errorMessage = "Telefone inválido! Use +2449xxxxxxxx"
            !currentPassword.matches(passwordRegex) -> errorMessage = "Senha deve ter 4 dígitos e 1 caracter especial"
            else -> {
                viewModelScope.launch {
                    try {
                        val existingUser = userDao.getUserByPhone(currentPhone)
                        if (existingUser != null) {
                            errorMessage = "Este número já está cadastrado!"
                        } else {
                            userDao.registerUser(
                                UserEntity(
                                    name = currentName,
                                    phone = currentPhone,
                                    password = currentPassword
                                )
                            )
                            onSuccess()
                        }
                    } catch (e: Exception) {
                        errorMessage = "Erro ao guardar no banco de dados."
                    }
                }
            }
        }
    }

    fun login(onSuccess: () -> Unit) {
        errorMessage = null
        val currentPhone = phone
        val currentPassword = password

        viewModelScope.launch {
            try {
                val user = userDao.getUserByPhone(currentPhone)
                if (user != null && user.password == currentPassword) {
                    onSuccess()
                } else {
                    errorMessage = "Credenciais incorretas."
                }
            } catch (e: Exception) {
                errorMessage = "Erro ao tentar fazer login."
            }
        }
    }

    fun clearFields() {
        name = ""
        phone = "+2449"
        password = ""
        errorMessage = null
    }
}