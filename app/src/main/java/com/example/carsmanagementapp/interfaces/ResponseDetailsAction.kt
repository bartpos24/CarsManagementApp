package com.example.carsmanagementapp.interfaces

import com.example.carsmanagementapp.Model.Car

interface ResponseDetailsAction {
    fun onSuccess(car: Car)
    fun onMessage(message: Int)
}