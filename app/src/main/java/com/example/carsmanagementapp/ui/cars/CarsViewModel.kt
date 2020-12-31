package com.example.carsmanagementapp.ui.cars

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class CarsViewModel : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var carList: ArrayList<Car> = ArrayList<Car>()

    var actualListOfCar: MutableLiveData<ArrayList<Car>> = MutableLiveData()



    private lateinit var ref: DatabaseReference

    fun loadDatabase(){
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    carList.clear()
                    for (h in snapshot.children) {
                        var car = h.getValue(Car::class.java)
                        carList.add(car!!)
                    }
                    actualListOfCar.value = carList
                }
            }
        })
    }

}