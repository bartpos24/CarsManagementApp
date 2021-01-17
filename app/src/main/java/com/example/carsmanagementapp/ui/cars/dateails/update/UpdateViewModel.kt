package com.example.carsmanagementapp.ui.cars.dateails.update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.interfaces.ResponseDatabaseAction
import com.example.carsmanagementapp.repositories.DatabaseRepository

class UpdateViewModel(private val repository: DatabaseRepository): ViewModel() {

    val messageLiveData = MutableLiveData<Int>()

    fun updateCar(car: Car) {
        repository.updateCar(car, object : ResponseDatabaseAction {
            override fun onSuccess(cars: ArrayList<Car>) {}
            override fun onMessage(message: Int) {
                messageLiveData.value = message
            }
        })
    }


}