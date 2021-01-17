package com.example.carsmanagementapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.User
import com.example.carsmanagementapp.interfaces.ResponseAuthentication
import com.example.carsmanagementapp.repositories.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel(private val repository: AuthenticationRepository): ViewModel() {

    val registerMesageLiveData = MutableLiveData<Int>()

    fun register(email: String, password: String, user: User) {
        repository.register(email, password, user, object : ResponseAuthentication {
            override fun onMessage(message: Int) {
                registerMesageLiveData.value = message
            }

            override fun onSuccess(currentUser: FirebaseUser) {}
        })
    }
}