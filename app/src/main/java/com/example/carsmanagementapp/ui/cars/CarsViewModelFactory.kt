package com.example.carsmanagementapp.ui.cars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.repositories.DatabaseRepository

class CarsViewModelFactory(private val repository: DatabaseRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CarsViewModel(repository) as T
    }
}