package com.example.carsmanagementapp.interfaces

import com.google.firebase.auth.FirebaseUser

interface ResponseAuthentication {
    fun onSuccess(currentUser: FirebaseUser)
    fun onMessage(message: Int)
}