package com.example.carsmanagementapp.ui.addCar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.repositories.DatabaseRepository
import com.example.carsmanagementapp.ui.cars.CarsViewModel

class AddViewModelFactory(private val repository: DatabaseRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddCarViewModel(repository) as T
    }
}