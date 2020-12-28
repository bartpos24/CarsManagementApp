package com.example.carsmanagementapp.ui.cars

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class CarsViewModel : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    var actualListOfCars = ArrayList<Car>()
    private var maxId = 0

    private var ref: DatabaseReference = database.getReference("Cars")

    fun loadDatabase() {
        var getData = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (h in snapshot.children) {
                    var car = h.getValue(Car::class.java)
                    actualListOfCars.add(car!!)
                }
            }
        }
        ref.child(auth.currentUser!!.uid).child("ActualCars")
        ref.addValueEventListener(getData)
        ref.addListenerForSingleValueEvent(getData)
    }








    fun addCar(newCar: Car) {
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    maxId = snapshot.childrenCount.toInt()
            }
        })
        newCar.id = maxId
        actualListOfCars.add(newCar)
        ref.child(auth.currentUser!!.uid).child("ActualCars").setValue(actualListOfCars)
    }

}