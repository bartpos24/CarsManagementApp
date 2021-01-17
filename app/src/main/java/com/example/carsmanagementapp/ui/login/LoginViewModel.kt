package com.example.carsmanagementapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.interfaces.ResponseAuthentication
import com.example.carsmanagementapp.repositories.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(private val repository: AuthenticationRepository): ViewModel() {

    val loginMessageLiveData = MutableLiveData<Int>()
    val userLiveData = MutableLiveData<FirebaseUser>()

    fun getLogin(email: String, password: String) {
        repository.login(email, password, object : ResponseAuthentication {
            override fun onSuccess(currentUser: FirebaseUser) {
                userLiveData.value = currentUser
            }
            override fun onMessage(message: Int) {
                loginMessageLiveData.value = message
            }
        })
    }
    fun getCurrentUser() : FirebaseUser? {
        return repository.currentUser()
    }

}