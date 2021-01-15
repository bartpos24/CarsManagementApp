package com.example.carsmanagementapp.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.repositories.DatabaseRepository

class StatisticsViewModelFactory(private val repository: DatabaseRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StatisticsViewModel(repository) as T
    }
}