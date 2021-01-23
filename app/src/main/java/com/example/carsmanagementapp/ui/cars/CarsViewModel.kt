package com.example.carsmanagementapp.ui.cars

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.interfaces.ResponseDatabaseAction
import com.example.carsmanagementapp.repositories.DatabaseRepository


import kotlin.collections.ArrayList

class CarsViewModel(private val repository: DatabaseRepository) : ViewModel() {

    val carsLiveData = MutableLiveData<ArrayList<Car>>()
    val carsMessageLiveData = MutableLiveData<Int>()

    fun getDatabase() {

        repository.loadDatabase(object : ResponseDatabaseAction{
            override fun onSuccess(cars: ArrayList<Car>) {
                carsLiveData.value = cars
            }

            override fun onMessage(errorMessage: Int) {
                carsMessageLiveData.value = errorMessage
            }
        })
    }

}