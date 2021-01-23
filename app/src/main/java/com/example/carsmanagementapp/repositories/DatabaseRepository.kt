package com.example.carsmanagementapp.repositories

import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.R.string.*
import com.example.carsmanagementapp.interfaces.ResponseDatabaseAction
import com.example.carsmanagementapp.interfaces.ResponseDetailsAction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DatabaseRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    private val refCar: DatabaseReference = FirebaseDatabase.getInstance().getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
    private val refSoldCar: DatabaseReference = FirebaseDatabase.getInstance().getReference("Cars").child(auth.currentUser!!.uid).child("SoldCars")

    private val actualCarList: ArrayList<Car> = ArrayList()
    private val soldedCarList: ArrayList<Car> = ArrayList()

    fun loadDatabase(callback: ResponseDatabaseAction) {
        refCar.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                callback.onMessage(com.example.carsmanagementapp.R.string.loadDatabaseError)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    actualCarList.clear()
                    for (h in snapshot.children) {
                        var car = h.getValue(Car::class.java)
                        actualCarList.add(car!!)
                    }
                    callback.onSuccess(actualCarList)
                    //actualCars.value = actualCarList
                }
            }
        })
    }
    fun loadSoldDatabase(callback: ResponseDatabaseAction) {
        refSoldCar.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                callback.onMessage(loadDatabaseError)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        var car = h.getValue(Car::class.java)
                        soldedCarList.add(car!!)
                    }
                    callback.onSuccess(soldedCarList)
                    //soldedCars.value = soldedCarList
                }
            }
        })
    }

    fun loadCar(id: String, callback: ResponseDetailsAction) {
        refCar.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                callback.onMessage(carLoadError)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        var car = h.getValue(Car::class.java)
                        if (car!!.id == id) {
                            callback.onSuccess(car)
                        }
                    }
                }
            }
        })
    }

    fun addCar(car: Car, callback: ResponseDatabaseAction) {
        var id = refCar.push().key
        car.id = id!!
        refCar.child(id).setValue(car).addOnCompleteListener {
            if (it.isSuccessful){
                callback.onMessage(add)
            }
            else
                callback.onMessage(addingFailed)
        }
    }

    fun deleteCar(car: Car, callback: ResponseDetailsAction) {
        refCar.child(car.id).setValue(null).addOnCompleteListener {
            if (it.isSuccessful){
                callback.onMessage(deletingSuccesful)
            }
            else {
                callback.onMessage(deletingError)
            }
        }
    }
    fun soldCar(car: Car, callback: ResponseDetailsAction) {
        refCar.child(car.id).setValue(null).addOnCompleteListener {
            if (it.isSuccessful) {
                refSoldCar.child(car.id).setValue(car).addOnCompleteListener {
                    if (it.isSuccessful) {
                        callback.onMessage(soldingSuccesful)
                    }
                    else
                        callback.onMessage(soldingError)
                }
            }
        }

    }
    fun updateCar(car: Car, callback: ResponseDatabaseAction) {
        refCar.child(car.id).setValue(car).addOnCompleteListener {
            if (it.isSuccessful) {
                callback.onMessage(updateSuccessful)
            }
            else
                callback.onMessage(errorUpdate)
        }
    }
}