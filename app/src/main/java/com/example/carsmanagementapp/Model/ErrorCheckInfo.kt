package com.example.carsmanagementapp.Model

import com.example.carsmanagementapp.Model.Enum.CarType
import com.example.carsmanagementapp.Model.Enum.EngineType
import java.io.Serializable

data class ErrorCheckInfo(var isError:Boolean, var brandErrStr:String, var modelErrStr:String, var ColorErrStr:String, var capErrStr:String, var powErrStr:String, var yearErrStr:String,
                          var engTypErrStr:String, var carTypErrStr:String) {

    constructor(): this(false,"","", "", "","","", "","")
}
