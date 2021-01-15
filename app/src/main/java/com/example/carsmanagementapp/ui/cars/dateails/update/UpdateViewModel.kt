package com.example.carsmanagementapp.ui.cars.dateails.update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.repositories.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UpdateViewModel(private val repository: DatabaseRepository): ViewModel() {

    fun updateCar(car: Car) {
        repository.updateCar(car)
    }


}