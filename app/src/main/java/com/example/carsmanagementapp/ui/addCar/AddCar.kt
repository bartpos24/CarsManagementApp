package com.example.carsmanagementapp.ui.addCar

import android.R.layout
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.Model.Enum.CarType
import com.example.carsmanagementapp.Model.Enum.EngineType
import com.example.carsmanagementapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.NumberFormatException
import java.lang.StringBuilder


class AddCar : Fragment() {


    private lateinit var addCarViewModel: AddCarViewModel
    private lateinit var engTypeSpinner: Spinner
    private lateinit var carTypeSpinner: Spinner
    private lateinit var brandEditText: EditText
    private lateinit var modelEditText: EditText
    private lateinit var capEditText: EditText
    private lateinit var powerEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var colorEditText: EditText
    private lateinit var addCarButton: Button
    private lateinit var mContext: Context
    private var typeOfEngine: EngineType = EngineType.NONE
    private var typeOfCar: CarType = CarType.NONE

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_car, container, false)
        engTypeSpinner = view!!.findViewById(R.id.engTypeSpinner)
        carTypeSpinner = view!!.findViewById(R.id.carTypeSpinner)
        addCarButton = view!!.findViewById(R.id.addCarButton)
        brandEditText = view!!.findViewById(R.id.brandEditText)
        modelEditText = view!!.findViewById(R.id.modelEditText)
        capEditText = view!!.findViewById(R.id.capEditText)
        powerEditText = view!!.findViewById(R.id.powerEditText)
        yearEditText = view!!.findViewById(R.id.yearEditText)
        colorEditText = view!!.findViewById(R.id.colorEditText)


        addCarViewModel = ViewModelProvider(this).get(AddCarViewModel::class.java)

        var engAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(mContext, R.array.engType, android.R.layout.simple_spinner_item)
        engAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        engTypeSpinner.adapter = engAdapter

        var carAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(mContext, R.array.carType, android.R.layout.simple_spinner_item)
        carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        carTypeSpinner.adapter = carAdapter


        /*val car1 = Car("", "Opel", "Astra", CarType.CABRIOLET, 1.8, 120,2008, EngineType.BENZINE, "Black")
        addCarViewModel.addCar(car1)
        val car2 = Car("", "Audi", "Q7", CarType.SUV, 4.0, 325,2013, EngineType.DIESEL, "Black")
        addCarViewModel.addCar(car2)
        val car3 = Car("", "Audi", "Q7", CarType.SUV, 4.0, 325,2014, EngineType.DIESEL, "White")
        addCarViewModel.addCar(car3)
        val car4 = Car("", "Toyota", "Rav4", CarType.SUV, 2.0, 170,2020, EngineType.BENZINE, "White")
        addCarViewModel.addCar(car4)
        val car5 = Car("", "Skoda", "Octavia", CarType.COMBI, 1.9, 90,1999, EngineType.DIESEL, "Srebrny")
        addCarViewModel.addCar(car5)
        val car6 = Car("", "Citroen", "C5", CarType.SEDAN, 2.0, 150,20010, EngineType.DIESEL, "Black")
        addCarViewModel.addCar(car6)
        val car7 = Car("", "Honda", "Civic", CarType.HETCHABCK, 1.4, 86,2006, EngineType.BENZINE, "Brąz")
        addCarViewModel.addCar(car7)
        val car8 = Car("", "Volkswagen", "Passat", CarType.SEDAN, 2.0, 170,2010, EngineType.BENZINE, "Srebrny")
        addCarViewModel.addCar(car8)
        val car9 = Car("", "Fiat", "126p", CarType.HETCHABCK, 0.6, 40,1997, EngineType.BENZINE, "Red")
        addCarViewModel.addCar(car9)
        val car10 = Car("", "Alfa", "Romeo", CarType.HETCHABCK, 2.0, 170,2010, EngineType.BENZINE, "Srebrny")
        addCarViewModel.addCar(car10)*/

        engTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                engSelectedView(position)
            }
        }
        carTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                carSelectedView(position)
            }
        }

        addCarButton.setOnClickListener {

            var validation = validationCar()
            if (validation == true) {
                val car = Car("", brandEditText.text.toString(), modelEditText.text.toString(), typeOfCar, capEditText.text.toString().toDouble(), powerEditText.text.toString().toInt(), yearEditText.text.toString().toInt(), typeOfEngine, colorEditText.text.toString())
                addCarViewModel.addCar(car)
                var sb = StringBuilder()
                sb.append(R.string.add).append(" ").append(car.brand).append(" ").append(car.model)
                Toast.makeText(mContext, sb, Toast.LENGTH_LONG).show()
                clearUI()
            }
            else {

            }
                Toast.makeText(mContext, R.string.addingFailed, Toast.LENGTH_LONG).show()

        }

        return view
    }
    private fun validationCar() : Boolean{
        var validation: Boolean = true
        if (brandEditText.text.toString() == "") {
            brandEditText.error = resources.getString(R.string.brandError)
            validation = false
        }
        if (modelEditText.text.toString() == "") {
            modelEditText.error = resources.getString(R.string.modelError)
            validation = false
        }
        if (colorEditText.text.toString() == "") {
            colorEditText.error = resources.getString(R.string.colorError)
            validation = false
        }
        var cap = capSelectedView()
        var pow = powerSelectedView()
        var year = yearSelectedView()
        if (cap == 0.0 || cap < 0.0) {
            capEditText.error = resources.getString(R.string.capacityError)
            validation = false
        }
        if (pow == 0 || pow < 0) {
            powerEditText.error = resources.getString(R.string.powerError)
            validation = false
        }
        if (year == 0 || year < 0) {
            yearEditText.error = resources.getString(R.string.yearError1)
            validation = false
        }

        if (typeOfEngine == EngineType.NONE) {
            Toast.makeText(mContext, R.string.engineError, Toast.LENGTH_LONG).show()
            validation = false
        }
        if (typeOfCar == CarType.NONE) {
            Toast.makeText(mContext, R.string.carError, Toast.LENGTH_LONG).show()
            validation = false
        }

        return validation

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addCarViewModel = ViewModelProvider(this).get(AddCarViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun clearUI() {
        brandEditText.text.clear()
        modelEditText.text.clear()
        capEditText.text.clear()
        powerEditText.text.clear()
        yearEditText.text.clear()
        colorEditText.text.clear()
        engTypeSpinner.setSelection(0)
        carTypeSpinner.setSelection(0)
    }

    fun engSelectedView(position: Int): EngineType{
        if (position == 0)
            typeOfEngine = EngineType.DIESEL
        else if (position == 1)
            typeOfEngine = EngineType.BENZINE
        else if (position == 2)
            typeOfEngine = EngineType.LPG
        else if (position == 3)
            typeOfEngine = EngineType.ELECTRIC
        else if (position == 4)
            typeOfEngine = EngineType.HYBRID
        else
            Toast.makeText(mContext, R.string.engineTypeError, Toast.LENGTH_LONG).show()

        return typeOfEngine
    }
    private fun carSelectedView(position: Int) {
        if (position == 0)
            typeOfCar = CarType.HETCHABCK
        else if (position == 1)
            typeOfCar = CarType.SEDAN
        else if (position == 2)
            typeOfCar = CarType.COMBI
        else if (position == 3)
            typeOfCar = CarType.SUV
        else if (position == 4)
            typeOfCar = CarType.CABRIOLET
        else if (position == 5)
            typeOfCar = CarType.VAN
        else if (position == 6)
            typeOfCar = CarType.PICKUP
        else if (position == 7)
            typeOfCar = CarType.COUPE
        else
            Toast.makeText(mContext, R.string.carTypeError, Toast.LENGTH_LONG).show()
    }
    private fun capSelectedView(): Double {
        var capacity: Double = 0.0
        try {
            capacity = capEditText.text.toString().toDouble()
        }
        catch (e: NumberFormatException) {
            capEditText.error = resources.getString(R.string.doubleError)
        }
        catch (e: Throwable) {
            capEditText.error = resources.getString(R.string.error)
        }

        return capacity

    }
    private fun powerSelectedView(): Int {
        var power: Int = 0
        try {
            power = powerEditText.text.toString().toInt()
        }
        catch (e: NumberFormatException) {
            powerEditText.error = resources.getString(R.string.intError)
        }
        catch (e: Throwable) {
            powerEditText.error = resources.getString(R.string.error)
        }

        return power
    }
    private fun yearSelectedView(): Int {
        var year: Int = 0
        try {
            year = yearEditText.text.toString().toInt()
        }
        catch (e: NumberFormatException) {
            yearEditText.error = resources.getString(R.string.yearError)
        }
        catch (e: Throwable) {
            yearEditText.error = resources.getString(R.string.error)
        }

        return year
    }

}