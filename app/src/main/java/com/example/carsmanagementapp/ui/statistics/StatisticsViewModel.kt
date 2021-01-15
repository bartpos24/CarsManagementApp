package com.example.carsmanagementapp.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.Model.Enum.CarType
import com.example.carsmanagementapp.Model.Enum.EngineType
import com.example.carsmanagementapp.repositories.DatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StatisticsViewModel(private val repository: DatabaseRepository) : ViewModel() {
    /*private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private var ref: DatabaseReference = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
    var allCars: ArrayList<Car> = ArrayList<Car>()
    var carsSold: ArrayList<Car> = ArrayList<Car>()

    var actualCars: MutableLiveData<ArrayList<Car>> = MutableLiveData()
    var soldCars: MutableLiveData<ArrayList<Car>> = MutableLiveData()
*/


    fun getDatabase(): LiveData<ArrayList<Car>> {
        return repository.getDatabase()
    }
    fun getSoldDatabase(): LiveData<java.util.ArrayList<Car>> {
        return repository.getSoldDatabase()
    }

    /*fun loadDatabase() {
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("ActualCars")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        val car = h.getValue(Car::class.java)
                        allCars.add(car!!)
                    }
                    actualCars.value = allCars
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    fun loadSoldDatabase() {
        ref = database.getReference("Cars").child(auth.currentUser!!.uid).child("SoldCars")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        val car = h.getValue(Car::class.java)
                        carsSold.add(car!!)
                    }
                    soldCars.value = carsSold
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }*/

    fun loadPower(cars: ArrayList<Car>): ArrayList<Int> {
        var powerList: ArrayList<Int> = ArrayList<Int>()
        powerList.clear()

        var pMin: Int = 0
        var p100: Int = 0
        var p150: Int = 0
        var p200: Int = 0
        var p300: Int = 0
        var p400: Int = 0

        for (i in cars) {
            if (i.power <= 100)
                pMin += 1
            else if ((i.power > 100) && (i.power < 150))
                p100 += 1
            else if ((i.power >= 150) && (i.power < 200))
                p150 += 1
            else if ((i.power >= 200) && (i.power < 300))
                p200 += 1
            else if ((i.power >= 300) && (i.power < 400))
                p300 += 1
            else if (i.power >= 400)
                p400 += 1
        }

        powerList.add(pMin)
        powerList.add(p100)
        powerList.add(p150)
        powerList.add(p200)
        powerList.add(p300)
        powerList.add(p400)

        return powerList
    }

    fun loadCarType(cars: ArrayList<Car>): ArrayList<Int> {
        var actualCarTypeList: ArrayList<Int> = ArrayList<Int>()
        actualCarTypeList.clear()

        var none: Int = 0
        var hetch: Int = 0
        var sedan: Int = 0
        var combi: Int = 0
        var suv: Int = 0
        var cabrio: Int = 0
        var van: Int = 0
        var pickup: Int = 0
        var coupe: Int = 0
        for (i in cars) {
            if (i.carType == CarType.NONE)
                none += 1
            else if (i.carType == CarType.HETCHABCK)
                hetch += 1
            else if (i.carType == CarType.SEDAN)
                sedan += 1
            else if (i.carType == CarType.COMBI)
                combi += 1
            else if (i.carType == CarType.SUV)
                suv += 1
            else if (i.carType == CarType.CABRIOLET)
                cabrio += 1
            else if (i.carType == CarType.VAN)
                van += 1
            else if (i.carType == CarType.PICKUP)
                pickup += 1
            else if (i.carType == CarType.COUPE)
                coupe += 1
        }
        actualCarTypeList.add(none)
        actualCarTypeList.add(hetch)
        actualCarTypeList.add(sedan)
        actualCarTypeList.add(combi)
        actualCarTypeList.add(suv)
        actualCarTypeList.add(cabrio)
        actualCarTypeList.add(van)
        actualCarTypeList.add(pickup)
        actualCarTypeList.add(coupe)

        return actualCarTypeList
    }
    fun loadEngineType(cars: ArrayList<Car>): ArrayList<Int> {

        var engineTypeList: ArrayList<Int> = ArrayList<Int>()
        engineTypeList.clear()

        var none: Int = 0
        var diesel: Int = 0
        var benzine: Int = 0
        var lpg: Int = 0
        var electric: Int = 0
        var hybrid: Int = 0

        for (i in cars) {
            if (i.engType == EngineType.NONE)
                none += 1
            else if (i.engType == EngineType.DIESEL)
                diesel += 1
            else if (i.engType == EngineType.BENZINE)
                benzine += 1
            else if (i.engType == EngineType.LPG)
                lpg += 1
            else if (i.engType == EngineType.ELECTRIC)
                electric += 1
            else if (i.engType == EngineType.HYBRID)
                hybrid += 1

        }
        engineTypeList.add(none)
        engineTypeList.add(diesel)
        engineTypeList.add(benzine)
        engineTypeList.add(lpg)
        engineTypeList.add(electric)
        engineTypeList.add(hybrid)


        return engineTypeList
    }
}