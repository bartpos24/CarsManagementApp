package com.example.carsmanagementapp.ui.addCar

import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.Model.Enum.CarType
import com.example.carsmanagementapp.Model.Enum.EngineType
import com.example.carsmanagementapp.Model.ErrorCheckInfo
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.interfaces.ResponseDatabaseAction
import com.example.carsmanagementapp.repositories.DatabaseRepository
import java.util.*
import kotlin.collections.ArrayList

class AddCarViewModel(private val repository: DatabaseRepository): ViewModel() {

    val carsMessageLiveData = MutableLiveData<Int>()

    fun addCar(newCar: Car) {
        repository.addCar(newCar, object : ResponseDatabaseAction {
            override fun onSuccess(cars: ArrayList<Car>) {}
            override fun onMessage(message: Int) {
                carsMessageLiveData.value = message
            }
        })
    }


}