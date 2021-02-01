package com.example.carsmanagementapp.ui.cars.dateails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.interfaces.ResponseDatabaseAction
import com.example.carsmanagementapp.interfaces.ResponseDetailsAction
import com.example.carsmanagementapp.repositories.DatabaseRepository

class DetailsViewModel(private val repository: DatabaseRepository): ViewModel() {

    val messageLiveData = MutableLiveData<Int>()
    val carLiveData = MutableLiveData<Car>()

    fun getCar(id: String) {
        repository.loadCar(id, object : ResponseDetailsAction {
            override fun onSuccess(car: Car) {
                carLiveData.value = car
            }
            override fun onMessage(message: Int) {
                messageLiveData.value = message
            }
        })
    }
    fun soldCar(car: Car) {
        repository.soldCar(car, object : ResponseDetailsAction {
            override fun onSuccess(car: Car) {}
            override fun onMessage(message: Int) {
                messageLiveData.value = message
            }
        })
    }
    fun deleteCar(car: Car) {
        repository.deleteCar(car, object : ResponseDetailsAction {
            override fun onSuccess(car: Car) {}
            override fun onMessage(message: Int) {
                messageLiveData.value = message
            }
        })
    }

}