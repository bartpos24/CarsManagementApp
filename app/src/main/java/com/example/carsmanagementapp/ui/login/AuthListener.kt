package com.example.carsmanagementapp.ui.login

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}