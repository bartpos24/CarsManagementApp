package com.example.carsmanagementapp.ui.register

import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.User
import com.example.carsmanagementapp.repositories.AuthenticationRepository

class RegisterViewModel(private val repository: AuthenticationRepository): ViewModel() {

    fun register(email: String, password: String, user: User) {
        repository.register(email, password, user)
    }
}