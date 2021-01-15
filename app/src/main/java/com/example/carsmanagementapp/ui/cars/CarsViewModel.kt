package com.example.carsmanagementapp.ui.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.repositories.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class CarsViewModel(private val repository: DatabaseRepository) : ViewModel() {


    fun getDatabase(): LiveData<ArrayList<Car>> {
        return repository.getDatabase()
    }



}