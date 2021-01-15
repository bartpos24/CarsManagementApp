package com.example.carsmanagementapp.ui.addCar

import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.repositories.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AddCarViewModel(private val repository: DatabaseRepository): ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private var ref: DatabaseReference = database.getReference("Cars")

    /*fun addCar(newCar: Car) {
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
        var id = ref.push().key!!
        newCar.id = id
        ref.child(id).setValue(newCar)

    }*/

    fun addCar(newCar: Car) {
        repository.addCar(newCar)
    }

}