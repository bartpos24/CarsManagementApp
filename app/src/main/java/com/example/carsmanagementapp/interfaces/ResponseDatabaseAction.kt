package com.example.carsmanagementapp.interfaces

import com.example.carsmanagementapp.Model.Car

interface ResponseDatabaseAction {
    fun onSuccess(cars: ArrayList<Car>)
    fun onMessage(message: Int)
}