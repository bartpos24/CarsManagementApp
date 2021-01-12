package com.example.carsmanagementapp.ui.cars.dateails.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.Model.Enum.CarType
import com.example.carsmanagementapp.Model.Enum.EngineType
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.cars.dateails.DetailsViewModel
import java.lang.NumberFormatException

class UpdateActivity : AppCompatActivity() {

    private lateinit var updateBtn: Button

    private lateinit var brandET: EditText
    private lateinit var modelET: EditText
    private lateinit var yearET: EditText
    private lateinit var capacityET: EditText
    private lateinit var powerET: EditText
    private lateinit var colorET: EditText
    private lateinit var engTypeSpinner: Spinner
    private lateinit var carTypeSpinner: Spinner
    var typeOfCar = CarType.NONE
    var typeOfEngine = EngineType.NONE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        var car = intent.getSerializableExtra("carToUpdate") as Car

        updateBtn = findViewById(R.id.updateBtnUp)
        val updateViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        initUI()

        displayCar(car)

        engTypeSpinner.setSelection(getIndex(engTypeSpinner, car.engType.toString()))
        carTypeSpinner.setSelection(getIndex(carTypeSpinner, car.carType.toString()))



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

        updateBtn.setOnClickListener {
            var cap = capSelectedView()
            var pow = powerSelectedView()
            var year = yearSelectedView()
            if (cap != 0.0 && pow != 0 && year != 0 && typeOfEngine != EngineType.NONE && typeOfCar != CarType.NONE) {
                if (brandET.text.toString() == "") {
                    brandET.error = resources.getString(R.string.brandError)
                }
                else if (modelET.text.toString() == "") {
                    modelET.error = resources.getString(R.string.modelError)
                }
                else if (colorET.text.toString() == "") {
                    colorET.error = resources.getString(R.string.colorError)
                }
                else {
                    val car = Car(car.id, brandET.text.toString(), modelET.text.toString(), typeOfCar, cap, pow, year, typeOfEngine, colorET.text.toString())
                    updateViewModel.updateCar(car)
                    var sb: String = ""
                    sb = resources.getString(R.string.update) + " ${car.brand} ${car.model}"
                    Toast.makeText(this, sb , Toast.LENGTH_LONG).show()
                }

            }
        }




    }

    private fun capSelectedView(): Double {
        var capacity: Double = 0.0
        try {
            capacity = capacityET.text.toString().toDouble()
        }
        catch (e: NumberFormatException) {
            Toast.makeText(this, R.string.doubleError, Toast.LENGTH_LONG).show()
        }
        catch (e: Throwable) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
        }

        return capacity

    }
    private fun powerSelectedView(): Int {
        var power: Int = 0
        try {
            power = powerET.text.toString().toInt()
        }
        catch (e: NumberFormatException) {
            Toast.makeText(this, R.string.intError, Toast.LENGTH_LONG).show()
        }
        catch (e: Throwable) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
        }

        return power
    }
    private fun yearSelectedView(): Int {
        var year: Int = 0
        try {
            year = yearET.text.toString().toInt()
        }
        catch (e: NumberFormatException) {
            Toast.makeText(this, R.string.yearError, Toast.LENGTH_LONG).show()
        }
        catch (e: Throwable) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
        }

        return year
    }
    private fun getIndex(spinner: Spinner, myString: String): Int {
        var index: Int = 0
        var h = spinner.count - 1
        for (i in 0..h) {
            if (myString == spinner.adapter.getItem(i)) {
                index = i
                break
            }
        }
        return index
    }
    private fun engSelectedView(position: Int): EngineType {
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
            Toast.makeText(this, R.string.engineError, Toast.LENGTH_LONG).show()

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
            Toast.makeText(this, R.string.carError, Toast.LENGTH_LONG).show()
    }
    private fun initUI() {
        brandET = findViewById(R.id.brandEditTextUp)
        modelET = findViewById(R.id.modelEditTextUp)
        yearET = findViewById(R.id.yearEditTextUp)
        capacityET = findViewById(R.id.capEditTextUp)
        powerET = findViewById(R.id.powerEditTextUp)
        colorET = findViewById(R.id.colorEditTextUp)
        engTypeSpinner = findViewById(R.id.engTypeSpinnerUp)
        carTypeSpinner = findViewById(R.id.carTypeSpinnerUp)

        updateBtn = findViewById(R.id.updateBtnUp)

        var engAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.engType, android.R.layout.simple_spinner_item)
        engAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        engTypeSpinner.adapter = engAdapter

        var carAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.carType, android.R.layout.simple_spinner_item)
        carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        carTypeSpinner.adapter = carAdapter
    }
    private fun displayCar(car: Car) {
        brandET.text = Editable.Factory.getInstance().newEditable(car.brand)
        modelET.text = Editable.Factory.getInstance().newEditable(car.model)
        yearET.text = Editable.Factory.getInstance().newEditable(car.year.toString())
        capacityET.text = Editable.Factory.getInstance().newEditable(car.engCap.toString())
        powerET.text = Editable.Factory.getInstance().newEditable(car.power.toString())
        colorET.text = Editable.Factory.getInstance().newEditable(car.color)
    }
}