package com.example.carsmanagementapp.ui.cars.dateails

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailsViewModel: ViewModel() {

    private var auth = FirebaseAuth.getInstance()
    private var database = FirebaseDatabase.getInstance()
    private var ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")

    private val _result = MutableLiveData<String>().apply {
        value = ""
    }
    val result: LiveData<String> = _result

    var carDetails: MutableLiveData<Car> = MutableLiveData()


    fun loadCar(id: String){
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        val car = h.getValue(Car::class.java)
                        if (id == car!!.id) {
                            carDetails.value = car
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun soldCar(car: Car) {
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
    }
    fun deleteCar(car: Car) {
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
        ref.child(car.id).setValue(null).addOnCompleteListener {
            if (it.isSuccessful) {
                _result.value = R.string.deletingSuccesful.toString()
            }
            else {
                _result.value = it.exception.toString()
            }
        }
    }

}