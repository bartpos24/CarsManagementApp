package com.example.carsmanagementapp.ui.prognosis

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PrognosisViewModel : ViewModel() {

    private var database = FirebaseDatabase.getInstance()
    private var auth = FirebaseAuth.getInstance()
    private var ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
    var actualCarList: MutableLiveData<ArrayList<Car>> = MutableLiveData()
    var carList: ArrayList<Car> = ArrayList()

    fun loadDatabase() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    carList.clear()
                    for (h in snapshot.children) {
                        val car = h.getValue(Car::class.java)
                        carList.add(car!!)
                    }
                    actualCarList.value = carList
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}