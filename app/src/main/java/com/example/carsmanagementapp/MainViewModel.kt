package com.example.carsmanagementapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.User
import com.example.carsmanagementapp.interfaces.ResponseMainAction
import com.example.carsmanagementapp.repositories.AuthenticationRepository

class MainViewModel(private val repository: AuthenticationRepository): ViewModel() {

    val mainMessageLiveData = MutableLiveData<Int>()
    val userLiveData = MutableLiveData<User>()

    fun getUser() {
        repository.getUser(object : ResponseMainAction {
            override fun onSuccess(user: User) {
                userLiveData.value = user
            }

            override fun onMessage(message: Int) {
                mainMessageLiveData.value = message
            }
        })
    }
    fun logout() {
        repository.signOut(object : ResponseMainAction {
            override fun onSuccess(user: User) {}
            override fun onMessage(message: Int) {
                mainMessageLiveData.value = message
            }
        })
    }
}