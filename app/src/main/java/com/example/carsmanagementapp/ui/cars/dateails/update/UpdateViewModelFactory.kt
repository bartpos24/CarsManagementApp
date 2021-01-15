package com.example.carsmanagementapp.ui.cars.dateails.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.repositories.DatabaseRepository

class UpdateViewModelFactory(private val repository: DatabaseRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpdateViewModel(repository) as T
    }
}