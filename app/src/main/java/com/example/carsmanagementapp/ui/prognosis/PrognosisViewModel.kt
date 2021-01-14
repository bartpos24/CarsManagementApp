package com.example.carsmanagementapp.ui.prognosis

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

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
    fun prognosisValidation(car1: Car, car2: Car): Car {

        // Obliczanie funkcji gaussa dla pojemności
        var gaussCapCar1 = exp(-0.3*(car1.engCap - 2.0))
        var gaussCapCar2 = exp(-0.3*(car2.engCap - 2.0))



        // Obliczanie funkcji gaussa dla mocy
        var gaussPowerCar1 = exp(-60*(car1.power - 150).toDouble())
        var gaussPowerCar2 = exp(-60*(car2.power - 150).toDouble())



        // Obliczanie funkcji gaussa dla roku
        var gaussYearCar1 = exp(-5*(car1.year - 2019).toDouble())
        var gaussYearCar2 = exp(-5*(car2.year - 2019).toDouble())



        var gaussCapMin: Double
        var gaussPowerMin: Double
        var gaussYearMin: Double

        // Obliczanie min gaussa dla pojemności
        if (gaussCapCar1 <= gaussCapCar2) {
            gaussCapMin = gaussCapCar1
        }
        else {
            gaussCapMin = gaussCapCar2
        }

        // Obliczanie min gaussa dla mocy
        if (gaussPowerCar1 <= gaussPowerCar2) {
            gaussPowerMin = gaussPowerCar1
        }
        else {
            gaussPowerMin = gaussPowerCar2
        }

        // Obliczanie min gaussa dla roku
        if (gaussYearCar1 <= gaussYearCar2) {
            gaussYearMin = gaussYearCar1
        }
        else {
            gaussYearMin = gaussYearCar2
        }

        // Obliczanie decyzji rozmytych
        var fuzzyDecision1 = (0.5 * gaussYearCar1) + (0.3 * gaussPowerCar1) + (0.2 * gaussCapCar1)
        var fuzzyDecision2 = (0.5 * gaussYearCar2) + (0.3 * gaussPowerCar2) + (0.2 * gaussCapCar2)

        // Obliczanie koncowej decyzji
        var decision1 = ((gaussCapMin * fuzzyDecision1) + (gaussPowerMin * fuzzyDecision1) + (gaussYearMin * fuzzyDecision1)) / (gaussCapMin + gaussPowerMin + gaussYearMin)
        var decision2 = ((gaussCapMin * fuzzyDecision2) + (gaussPowerMin * fuzzyDecision2) + (gaussYearMin * fuzzyDecision2)) / (gaussCapMin + gaussPowerMin + gaussYearMin)

        // Porównanie wyników
        var betterCar: Car
        if (decision1 > decision2) {
            betterCar = car1
        }
        else {
            betterCar = car2
        }




        return betterCar
    }

    fun otherPrognosis(car1: Car, car2: Car): Car {
        var capCar1 = (1/(0.3 * sqrt(2 * PI))) * Math.E * (-(car1.engCap - 2.0).pow(2)/(2 * (0.3).pow(2)))
        var capCar2 = (1/(0.3 * sqrt(2 * PI))) * Math.E * (-(car2.engCap - 2.0).pow(2)/(2 * (0.3).pow(2)))

        var powerCar1 = (1/(60 * sqrt(2 * PI))) * Math.E * (-(car1.power - 150.0).pow(2)/(2 * (60.0).pow(2)))
        var powerCar2 = (1/(60 * sqrt(2 * PI))) * Math.E * (-(car2.power - 150.0).pow(2)/(2 * (60.0).pow(2)))

        var yearCar1 = (1/(5 * sqrt(2 * PI))) * Math.E * (-(car1.year - 2019.0).pow(2)/(2 * (5.0).pow(2)))
        var yearCar2 = (1/(5 * sqrt(2 * PI))) * Math.E * (-(car2.year - 2019.0).pow(2)/(2 * (5.0).pow(2)))

        var gaussCapMin: Double
        var gaussPowerMin: Double
        var gaussYearMin: Double

        // Obliczanie min gaussa dla pojemności
        if (capCar1 <= capCar2) {
            gaussCapMin = capCar1
        }
        else {
            gaussCapMin = capCar2
        }

        // Obliczanie min gaussa dla mocy
        if (powerCar1 <= powerCar2) {
            gaussPowerMin = powerCar1
        }
        else {
            gaussPowerMin = powerCar2
        }

        // Obliczanie min gaussa dla roku
        if (yearCar1 <= yearCar2) {
            gaussYearMin = yearCar1
        }
        else {
            gaussYearMin = yearCar2
        }

        // Obliczanie decyzji rozmytych
        var fuzzyDecision1 = (0.5 * yearCar1) + (0.3 * powerCar1) + (0.2 * capCar1)
        var fuzzyDecision2 = (0.5 * yearCar2) + (0.3 * powerCar2) + (0.2 * capCar2)

        // Obliczanie koncowej decyzji
        var decision1 = ((gaussCapMin * fuzzyDecision1) + (gaussPowerMin * fuzzyDecision1) + (gaussYearMin * fuzzyDecision1)) / (gaussCapMin + gaussPowerMin + gaussYearMin)
        var decision2 = ((gaussCapMin * fuzzyDecision2) + (gaussPowerMin * fuzzyDecision2) + (gaussYearMin * fuzzyDecision2)) / (gaussCapMin + gaussPowerMin + gaussYearMin)

        // Porównanie wyników
        var betterCar: Car
        if (decision1 > decision2) {
            betterCar = car1
        }
        else {
            betterCar = car2
        }

        return betterCar

    }

}