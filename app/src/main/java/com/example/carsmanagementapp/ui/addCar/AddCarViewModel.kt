package com.example.carsmanagementapp.ui.addCar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.interfaces.ResponseDatabaseAction
import com.example.carsmanagementapp.repositories.DatabaseRepository

class AddCarViewModel(private val repository: DatabaseRepository): ViewModel() {

    val carsMessageLiveData = MutableLiveData<Int>()

    fun addCar(newCar: Car) {
        repository.addCar(newCar, object : ResponseDatabaseAction {
            override fun onSuccess(cars: ArrayList<Car>) {}
            override fun onMessage(message: Int) {
                carsMessageLiveData.value = message
            }
        })
    }

}