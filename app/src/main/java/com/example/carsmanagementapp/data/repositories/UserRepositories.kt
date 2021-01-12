package com.example.carsmanagementapp.data.repositories

import com.example.carsmanagementapp.data.firebase.FirebaseSource

class UserRepositories(private val firebase: FirebaseSource) {

    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}