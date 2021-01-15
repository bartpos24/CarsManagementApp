package com.example.carsmanagementapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carsmanagementapp.Model.Car
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DatabaseRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    private val refCar: DatabaseReference = FirebaseDatabase.getInstance().getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
    private val refSoldCar: DatabaseReference = FirebaseDatabase.getInstance().getReference("Cars").child(auth.currentUser!!.uid).child("SoldCars")

    private val actualCarList: ArrayList<Car> = ArrayList()
    private val actualCars: MutableLiveData<ArrayList<Car>> = MutableLiveData<ArrayList<Car>>()
    private val soldedCarList: ArrayList<Car> = ArrayList()
    private val soldedCars: MutableLiveData<ArrayList<Car>> = MutableLiveData<ArrayList<Car>>()
    private val carDetails: MutableLiveData<Car> = MutableLiveData<Car>()



    private fun loadDatabase() {
        refCar.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        var car = h.getValue(Car::class.java)
                        actualCarList.add(car!!)
                    }
                    actualCars.value = actualCarList
                }
            }
        })
    }
    private fun loadSoldDatabase() {
        refSoldCar.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        var car = h.getValue(Car::class.java)
                        soldedCarList.add(car!!)
                    }
                    soldedCars.value = soldedCarList
                }
            }
        })
    }
    private fun loadCar(id: String) {
        refCar.addValueEventListener(object : ValueEventListener {
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
    fun getDatabase(): LiveData<ArrayList<Car>> {
        loadDatabase()
        return actualCars
    }
    fun getSoldDatabase(): LiveData<ArrayList<Car>> {
        loadSoldDatabase()
        return soldedCars
    }

    fun addCar(car: Car) {
        var id = refCar.push().key
        car.id = id!!
        refCar.child(id).setValue(car)
    }
    fun getCar(id: String): LiveData<Car> {
        loadCar(id)
        return  carDetails
    }
    fun deleteCar(car: Car) {
        refCar.child(car.id).setValue(null)
    }
    fun soldCar(car: Car) {
        refCar.child(car.id).setValue(null)
        refSoldCar.child(car.id).setValue(car)
    }
    fun updateCar(car: Car) {
        refCar.child(car.id).setValue(car)
    }
}