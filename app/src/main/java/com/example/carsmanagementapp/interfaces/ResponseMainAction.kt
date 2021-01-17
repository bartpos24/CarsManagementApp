package com.example.carsmanagementapp.interfaces

import com.example.carsmanagementapp.Model.User

interface ResponseMainAction {
    fun onSuccess(user: User)
    fun onMessage(message: Int)
}