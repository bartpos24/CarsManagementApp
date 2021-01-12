package com.example.carsmanagementapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.data.repositories.UserRepositories

class AuthViewModelFactory(private val repository: UserRepositories):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}