package com.example.carsmanagementapp.ui.cars.dateails

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.repositories.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailsViewModel(private val repository: DatabaseRepository): ViewModel() {

    private var auth = FirebaseAuth.getInstance()
    private var database = FirebaseDatabase.getInstance()
    private var ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")

    private val _result = MutableLiveData<String>().apply {
        value = ""
    }
    val result: LiveData<String> = _result

    var carDetails: MutableLiveData<Car> = MutableLiveData()

    fun loadCar(id: String): LiveData<Car> {
        return repository.getCar(id)
    }
    fun soldCar(car: Car) {
        repository.soldCar(car)
    }

    /*fun soldCar(car: Car) {
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
        ref.child(car.id).setValue(null).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = R.string.soldingSuccesful.toString()
            }
            else {
                _result.value = it.exception.toString()
            }
        }
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("SoldCars")
        ref.child(car.id).setValue(car)
    }*/
    fun deleteCar(car: Car) {
        repository.deleteCar(car)
    }
    /*fun deleteCar(car: Car) {
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
        ref.child(car.id).setValue(null).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = R.string.deletingSuccesful.toString()
            }
            else {
                _result.value = it.exception.toString()
            }
        }
    }*/

}