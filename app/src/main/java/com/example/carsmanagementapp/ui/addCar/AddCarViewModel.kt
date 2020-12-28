package com.example.carsmanagementapp.ui.addCar

import android.app.ActivityManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import java.util.ArrayList
import kotlin.text.StringBuilder

class AddCarViewModel: ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    var carList: ArrayList<Car> = ArrayList<Car>()
    var actualListOfCars = MutableLiveData<ArrayList<Car>>(arrayListOf())
    private var maxId = 0

    private var ref: DatabaseReference = database.getReference("Cars")

    fun addCar(newCar: Car) {
        ref = database.getReference("Cars")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    maxId = snapshot.childrenCount.toInt()
            }
        })
        newCar.id = carList.size
        //actualListOfCars.add(newCar)
        carList.add(newCar)
        ref.child(auth.currentUser!!.uid).child("ActualCars").child(newCar.id.toString()).setValue(newCar)
    }

    fun loadDatabase() {
        var getValue = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var sv = StringBuilder()
                    for (h in snapshot.children) {
                        val car = h.getValue(Car::class.java)
                        carList.add(car!!)
                        sv.append("${car!!.brand} ${car!!.model}\n")
                    }
                    Log.e("Car", sv.toString())
                }
                else
                    Log.e("Error", "Snapshot doesn't exists")
            }
        }
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
        ref.addValueEventListener(getValue)
        ref.addListenerForSingleValueEvent(getValue)

    }

}