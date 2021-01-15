package com.example.carsmanagementapp.ui.prognosis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.repositories.DatabaseRepository

class PrognosisViewModelFactory(private val repository: DatabaseRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PrognosisViewModel(repository) as T
    }
}