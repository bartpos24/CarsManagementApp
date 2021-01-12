package com.example.carsmanagementapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.data.repositories.UserRepositories

class MainViewModelFactory(private val repository: UserRepositories): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}