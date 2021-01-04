package com.example.carsmanagementapp.ui.cars.dateails.update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UpdateViewModel: ViewModel() {
    private var auth = FirebaseAuth.getInstance()
    private var database = FirebaseDatabase.getInstance()
    private var ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")


    fun updateCar(car: Car) {
        ref.child(car.id).setValue(car)
    }


}