package com.example.carsmanagementapp.Model

import com.example.carsmanagementapp.Model.Enum.CarType
import com.example.carsmanagementapp.Model.Enum.EngineType

data class Car(var id: String, var brand: String, var model: String, var carType: CarType, var engCap: Double, var power: Int, var year: Int, var engType: EngineType, var color: String) {

    constructor(): this("","","",CarType.NONE, 0.0,0,0,EngineType.NONE,"")

}