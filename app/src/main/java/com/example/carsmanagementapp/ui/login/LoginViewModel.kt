package com.example.carsmanagementapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.repositories.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(private val repository: AuthenticationRepository): ViewModel() {

    fun getLogin(email: String, password: String): LiveData<FirebaseUser?> {
        return repository.getLogin(email, password)
    }
    fun getCurrentUser(): FirebaseUser?{
        return repository.currentUser()
    }
}